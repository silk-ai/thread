package thread;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunctionThreadController
{
  @RequestMapping("/thread")
  public FunctionThread thread(@RequestParam(name = "params") ArrayList<String> name) 
  {
    try {
      Weaver.debug("/Users/carloslahrssen/Code/silk-ai/thimble/BigIntArray.java",
          "/Users/carloslahrssen/Code/silk-ai/thimble/MyIntArray.java");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new FunctionThread(name);
  }
}