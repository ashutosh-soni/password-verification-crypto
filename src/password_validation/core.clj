(ns password-validation.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [crypto.password.scrypt :as scrypt]
            [password-validation.response :as response])
  (:gen-class))



; Logic
(defn scrypt-password
  "pwd [String]: password"
  [pwd]
  (scrypt/encrypt pwd 65536 16 1))

(defn check-scrypt-password
  "check the scrypted password with hash password
   pwd [String]: password that you wanna test
   hash [ String ] : it is a hash password"
  [pwd hash]
  (try
    (scrypt/check pwd hash)
    (catch Exception e
      false)))

(defn check-password [password hash-password]
  (let [matched? (check-scrypt-password password hash-password)]
    #_(println "postman testing" {:pwd password :hash hash-password :result matched?})
    (if matched?
      (response/matched-success)
      (response/matched-failed))))


; Simple Body Page
(defn test-hello-world [req]
  (response/res-ok "Hello world!"))

(defn match-password-handler
  [req]
  (try
    (let [password (get-in req [:headers "password"])
          hash-password (get-in req [:headers "hashpassword"])]

      #_(println "req object" (get-in req [:headers "hashpassword"]))
      (cond
        (nil? password) (response/res-bad
                         "Password is missing in request header with key: password")
        (nil? hash-password) (response/res-bad
                              "HashPassword is missing in request header with key: hashPassword")
        :else
        (response/res-ok (check-password password hash-password))))
    (catch Exception e (response/res-server-error (.getMessage e)))))


; Our main routes
(defroutes app-routes
  (GET "/" [] test-hello-world)
  (GET "/api/v0/match-password" [] match-password-handler)
  (route/not-found "Error, page not found!"))

; Our main entry function
(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))



#_(comment
    (check-scrypt-password "a" 2)

    (let [pwd "123"
          hash (scrypt-password pwd)]
      (println "Internal testing" {:pwd {:type (type pwd) :pwd pwd} :hash
                                   {:type (type hash) :hash hash}})
      (check-scrypt-password pwd hash)))