Wicket Rest Lambda
===

Module to write microservices with Wicket in a functional way, as we do with [Spark](http://sparkjava.com/):
```java
    public class MyApplication extends WebApplication 
    {
        @Override
        protected void init()
        {
            Map<String, Object> map = new HashMap<>();
            map.put("integer", 123);
            map.put("string", "message");
            
            LambdaRestMounter restMounter = new LambdaRestMounter(this);
            
            restMounter.get("/testget", (attributes) -> "hello!");
            restMounter.post("/testjson", (attributes) -> map, JSONObject::valueToString);
            
            //return id value from url segment
            restMounter.options("/testparam/${id}", (attributes) -> {
                PageParameters pageParameters = attributes.getPageParameters();
                return pageParameters.get("id");
               }
            );
        }
    }
```
