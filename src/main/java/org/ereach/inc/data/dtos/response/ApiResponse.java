package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse <T>{
	
	private T data;
	private boolean successful;
	private int statusCode;
}
