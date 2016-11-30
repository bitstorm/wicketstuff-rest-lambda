Wicket Rest Lambda
===

Module to write microservices with Wicket musch like we do with [Spark](http://sparkjava.com/):

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
        }
    }

