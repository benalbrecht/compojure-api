(ns example.data-spec
  (:require [compojure.api.sweet :refer [context POST resource]]
            [ring.util.http-response :refer [ok]]
            [spec-tools.spec :as spec]))

(def routes
  (context "/data-spec" []
    :tags ["data-spec"]
    :coercion :spec

    (POST "/plus" []
      :summary "plus with clojure.spec using data-specs"
      :body-params [x :- spec/int?, {y :- spec/int? 0}]
      :return {:total spec/int?}
      (ok {:total (+ x y)}))

    (context "/plus" []
      (resource
        {:get
         {:summary "data-driven plus with clojure.spec using data-specs"
          :parameters {:query-params {:x spec/int?, :y spec/int?}}
          :responses {200 {:schema {:total spec/int?}}}
          :handler (fn [{{:keys [x y]} :query-params}]
                     (ok {:total (+ x y)}))}}))))
