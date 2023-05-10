package bar.foo.controllers;

import bar.foo.services.BS;
import bar.foo.services.internal.GCBT;
import bar.foo.services.internal.IS;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty("services.igor.enabled")
@RestController
@RequestMapping("/gcb")
public class GCBController {
  private IS iS;
  private BS bS;

  @Autowired
  public GCBController(IS iS, BS bS) {
    this.iS = iS;
    this.bS = bS;
  }

  @ApiOperation(value = "Retrieve the list of Google Cloud Build accounts", response = List.class)
  @GetMapping(value = "/acs")
  List<String> getAs() {
    return iS.getGCBAs();
  }

  @ApiOperation(
      value = "Retrieve the list of gcbts for a given account",
      response = List.class)
  @GetMapping(value = "/ts/{account}")
  List<GCBT> getGCBTs(
      @PathVariable("account") String account) {
    return bS.getGCBTsForAccount(account);
  }
}