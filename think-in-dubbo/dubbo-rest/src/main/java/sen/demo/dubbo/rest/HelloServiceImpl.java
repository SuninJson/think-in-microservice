package sen.demo.dubbo.rest;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import sen.demo.hello.api.HelloService;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@DubboService(protocol = "rest")
public class HelloServiceImpl implements HelloService {

    @Override
    @Path("/hello/{name}")
    public String sayHello(@PathParam("name") String name) {
        return "Hello," + name;
    }
}
