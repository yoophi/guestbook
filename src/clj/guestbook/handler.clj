(ns guestbook.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [guestbook.layout :refer [error-page]]
            [guestbook.routes.home :refer [home-routes]]
            [guestbook.routes.ws :refer [websocket-routes]]
            [compojure.route :as route]
            [guestbook.middleware :as middleware]))

(def app-routes
  (routes
    #'websocket-routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))
(def app (middleware/wrap-base #'app-routes))
