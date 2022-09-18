package com.gatling.tests

import com.fasterxml.jackson.databind.Module.SetupContext

import scala.concurrent.duration.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

class AddComputerAndSearch extends Simulation {

	val httpProtocol = http
		.baseUrl("https://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36")



val createcomputer=exec(http("Homepage")
		.get("/computers")
	)
		.pause(0)
		.exec(http("MewComputerPage")
			.get("/computers/new")
		)
		.pause(0)
		.exec(http("SaveComputer")
			.post("/computers")

			.formParam("name", "DevarajuComputer")
			.formParam("introduced", "2010-01-01")
			.formParam("discontinued", "2012-10-01")
			.formParam("company", "13"))
		.pause(0)

	val search= exec(http("SearchComputer")
		.get("/computers?f=devaraju")
	)

	val scn = scenario("AddComputerAndSearch").exec(createcomputer,search)

	val Add=scenario("Add").exec(createcomputer)
	val Search=scenario("Search").exec(search)



	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}