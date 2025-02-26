package io.handsone.tasktracker.application;

import io.handsone.tasktracker.security.AuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("local")
@Tag(name = "Test", description = "This API allows testing basic functionalities and authentication features.")
public class TestController {

  @Operation(
      summary = "Test API",
      description = "This API endpoint receives a TestRequest object and returns a personalized greeting message based on the 'name' field in the request body.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(schema = @Schema(implementation = TestResponse.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request - Invalid Input", content = @Content)
      }
  )
  @PostMapping("/v0/test")
  public ResponseEntity<TestResponse> test(@RequestBody TestRequest testRequest) {
    return ResponseEntity.ok(new TestResponse("Hello " + testRequest.name + "!"));
  }

  @AuthRequired
  @Operation(
      summary = "Test API with Authentication",
      description = "This API endpoint receives a TestRequest object and returns a personalized greeting message based on the 'name' field in the request body.",
      security = @SecurityRequirement(name = "bearerAuth"),
      responses = {
          @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(schema = @Schema(implementation = TestResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or Missing Authentication Token", content = @Content)
      }
  )
  @PostMapping("/v0/test-auth")
  public ResponseEntity<TestResponse> testAuth(@RequestBody TestRequest testRequest) {
    return ResponseEntity.ok(new TestResponse("Hello " + testRequest.name + "!"));
  }

  @Data
  @AllArgsConstructor
  public static class TestResponse {

    @Schema(description = "The greeting message for the user.", example = "Hello John!")
    String message;
  }

  @Data
  public static class TestRequest {

    @Schema(description = "The name of the user to greet.", example = "John", required = true)
    String name;
  }
}
