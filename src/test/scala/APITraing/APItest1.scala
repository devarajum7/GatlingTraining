package APITraing



import com.fasterxml.jackson.databind.Module.SetupContext

import scala.concurrent.duration.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.http.check.HttpCheckScope.Url
import io.gatling.jdbc.Predef.*
class APItest1 extends Simulation {
//protocol
val httpProtocol = http
  .baseUrl("https://reqres.in/api/users")

//Scenario

val feeder=csv("data.csv").random
feed(feeder)
val scn = scenario(scenarioName = "GetUser")
  .exec
  (http(requestName = "GetUser")
    .get("/#{name}")
    .check(status.is(200))
    .check(jsonPath(path="$.data[1].first_name").is(expected ="Lindsay"))
    )

//setUp()


  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)


}
