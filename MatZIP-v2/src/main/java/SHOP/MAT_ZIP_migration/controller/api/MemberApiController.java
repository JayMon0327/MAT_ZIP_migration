package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.dto.member.JoinMemberDto;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.member.UpdateMemberDto;
import SHOP.MAT_ZIP_migration.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "01.Member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/auth/joinMember")
    @Operation(summary = "사용자 등록", description = "Oauth를 포함해 사용자를 등록한다.")
    public ResponseDto<Integer> save(@Valid @RequestBody JoinMemberDto joinMemberDto) {
        memberService.SignUp(joinMemberDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    @Operation(summary = "사용자 변경", description = "사용자를 변경한다.")
    public ResponseDto<Integer> update(@Valid @RequestBody UpdateMemberDto updateMemberDto) {
        memberService.update(updateMemberDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
