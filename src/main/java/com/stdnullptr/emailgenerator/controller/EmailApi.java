package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import com.stdnullptr.emailgenerator.exception.InvalidArgumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * The purpose of this class is:<p>
 * <ul>
 * <li>To hold API description details, removing bloat from the implementation
 * <li>To define validation rules and error messages, again removing bloat from the implementation details.
 * </ul>
 */
@Tag(name = "Email", description = "Handles all operations related to generating emails through the Email Generator API.")
public abstract class EmailApi {
	protected static final String ERROR_QUERY_EMPTY = "Query parameters cannot be empty";

	/**
	 * Generates email addresses based on the criteria specified within the 'expression' query parameter, and the input parameters.
	 * The rules of this expression language are defined in <a href="https://github.com/stdNullPtr/Dynamic-Email-Generator/?tab=readme-ov-file#dynamic-email-generator">the README</a>
	 *
	 * @param queryParams a {@link MultiValueMap} containing the query parameters that define the rules and formats
	 *                    for email generation. These parameters should follow the custom expression language specified
	 *                    for email generation.
	 * @return a {@link ResponseEntity} object containing either the list of generated emails or error details,
	 * wrapped in {@link com.stdnullptr.emailgenerator.model.response.ApiResponse}.
	 * @throws HandlerMethodValidationException if the query parameters are empty or invalid as per the defined validation rules.
	 * @throws InvalidArgumentException         if the query parameters are not valid by structure as per the defined business logic.
	 * @throws InterpreterException             if the query parameters are invalid as per the defined expression language rules.
	 */
	@Operation(
			summary = "Generate email addresses using a custom expression language",
			parameters =
			@Parameter(name = "queryParams", description = "A map, that allows multiple values for one input name, of query parameters for email generation. For example, multiple 'strN' parameters can be 'str1=Ivan,Petar,Rado' and 'str2=gmail,yahoo'.",
					examples = {
							@ExampleObject(name = "Example simple input", value = "{\"str1\":[\"Ivan\",\"Petar\",\"Rado\"],\"str2\":[\"gmail\",\"yahoo\"],\"expression\":[\"first(str1, 3);raw(@);last(str2,4);raw(.com)\"]}", description = "As query parameters: '?expression=first(str1, 3);raw(@);last(str2,4);raw(.com)&str1=Ivan&str1=Petar&str1=Rado&str2=gmail&str2=yahoo'"),
							@ExampleObject(name = "Example conditional expression", value = "{\"expression\":[\"longer(lit(str1),lit(str2),lit(str3));first(str1, 3);raw(@);last(str2,4);raw(.com);eq(lit(str1),lit(str2),raw(.bg))\"],\"str1\":[\"Ivan\",\"Petar\",\"Radooo\"],\"str2\":[\"gmail\",\"yahoo\"],\"str3\":[\"test\",\"domain\"]}", description = "As query parameters: '?expression=longer(lit(str1),lit(str2),lit(str3));first(str1, 3);raw(@);last(str2,4);raw(.com);eq(lit(str1),lit(str2),raw(.bg))&str1=Ivan&str1=Petar&str1=Radooo&str2=gmail&str2=yahoo&str3=test&str3=domain'")
					},
					required = true)
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully generated emails.",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = com.stdnullptr.emailgenerator.model.response.ApiResponse.class),
							examples = {@ExampleObject(
									name = "Successfully generated simple emails",
									value = "{\"data\":[\"Iva@mail.com\",\"Iva@ahoo.com\",\"Pet@mail.com\",\"Pet@ahoo.com\",\"Rad@mail.com\",\"Rad@ahoo.com\"],\"message\":null}"
							), @ExampleObject(
									name = "Successfully generated conditional expression emails",
									value = "{\"data\":[\"Iva@mail.com\",\"Iva@mail.com\",\"Iva@ahoo.com\",\"Iva@ahoo.com\",\"Pet@mail.com\",\"Pet@mail.com\",\"Pet@ahoo.com\",\"Pet@ahoo.com\",\"testRad@mail.com\",\"domainRad@mail.com\",\"testRad@ahoo.com\",\"domainRad@ahoo.com\"],\"message\":null}"
							)}
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid or empty request parameters.",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = com.stdnullptr.emailgenerator.model.response.ApiResponse.class),
							examples = {
									@ExampleObject(
											name = "Occurs when 'expression' query parameter is not provided",
											value = "{\"data\":null,\"message\":\"An 'expression' string is required.\"}"
									),
									@ExampleObject(
											name = "Occurs when no input query parameters are provided",
											value = "{\"data\":null,\"message\":\"At least one input is required.\"}"
									),
									@ExampleObject(
											name = "Occurs when an unknown expression is detected",
											value = "{\"data\":null,\"message\":\"Unknown expression: firsst(str1, 3)\"}"
									)
							}
					)
			)
	})
	@SuppressWarnings("unused")
	abstract ResponseEntity<com.stdnullptr.emailgenerator.model.response.ApiResponse<?>> generateEmails(
			@NotEmpty(message = ERROR_QUERY_EMPTY)
			MultiValueMap<String, String> queryParams);
}
