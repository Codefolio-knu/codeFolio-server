package com.jandy.codeFolio.present.post;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.post.dto.PostCreateRequest;
import com.jandy.codeFolio.present.post.dto.PostCreateResponse;
import com.jandy.codeFolio.present.post.dto.PostListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Post API", description = "게시글 및 모집 관련 API 명세")
public interface PostControllerDocs {
    @Operation(
            summary = "새 게시글 등록 (팀/스터디 모집)",
            description = "인증된 사용자가 새로운 모집 게시글을 등록합니다. 사용자 Role은 서버에서 자동으로 결정됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 등록 성공",
                    content = @Content(schema = @Schema(implementation = PostCreateResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (작성자 ID가 존재하지 않음) 또는 SKILL_NOT_FOUND",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 형식 또는 필수 값 누락",
                    content = @Content
            )
    })
    PostCreateResponse createPost(@RequestBody PostCreateRequest request);

    @Operation(
            summary = "게시글 목록 조회 및 검색/정렬",
            description = """
                    페이지네이션을 사용하여 게시글 목록을 조회합니다. 
                    검색 조건(기술 스택, 모집 인원)과 정렬이 가능합니다.
                    
                    ✅ 정렬은 Pageable의 sort 파라미터를 사용합니다.
                    - 예시: `?sort=createdAt,desc` (최신순)
                    - 예시: `?sort=endDate,asc` (마감 임박순)
                    
                    ✅ 페이징 예시:
                    - `?page=0&size=10` (첫 페이지, 10개)
                    
                    ✅ 검색 예시:
                    - `?skillIds=1,2&capacity=3`
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 형식 (예: skillIds에 숫자가 아닌 값 입력)",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseWrapper<Page<PostListResponse>>> findAllPosts(
            @Parameter(
                    name = "skillIds",
                    description = "검색할 기술 스택 ID 목록 (쉼표 구분: 예: 1,5,9). 요청된 모든 스택을 포함하는 게시글을 조회합니다.",
                    in = ParameterIn.QUERY,
                    array = @ArraySchema(schema = @Schema(type = "integer", format = "int64"))
            )
            List<Long> skillIds,

            @Parameter(
                    name = "capacity",
                    description = "검색할 모집 인원 수 (예: 3)",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "integer")
            )
            Integer capacity,

            @Parameter(
                    description = """
                            페이지네이션 및 정렬 정보입니다.
                            - page: 조회할 페이지 번호 (0부터 시작)
                            - size: 한 페이지당 데이터 수
                            - sort: 정렬 기준 (예: `createdAt,desc` 또는 `endDate,asc`)
                            """
            )
            Pageable pageable
    );
}