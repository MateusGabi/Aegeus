package bar.foo.controllers;

import bar.foo.*;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class FooController {
  private final FooSelector fooService;

  @Autowired
  FooController(FooSelector fooService) {
    this.fooService = fooService;
  }

  @ApiOperation(value = "Retrieve the foo list", response = List.class)
  @GetMapping(value = "/fools")
  List<Object> getFools() {
    return fooService.select().getFools();
  }
}