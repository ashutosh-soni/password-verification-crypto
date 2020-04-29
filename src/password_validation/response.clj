(ns password-validation.response
  (:require [clojure.data.json :as json]))

(defn matched-success
  ([]
   {:isMatched true})
  ([message]
   {:isMatched true
    :message message}))

(defn matched-failed
  ([]
   {:isMatched false})
  ([message]
   {:isMatched false
    :message message}))

; Response helper
(defn res-ok
  "body [map], *Required: data is the body that must be send."
  [body]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str body)})

(defn res-bad [body]
  {:status  400
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:error body})})

(defn res-server-error [body]
  {:status  500
   :headers {"Content-Type" "text/json"}
   :body (json/write-str {:error body})})
