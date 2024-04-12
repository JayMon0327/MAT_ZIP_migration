package MATZIP_ver3.controller.api;

import MATZIP_ver3.dto.ResponseDto;
import MATZIP_ver3.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChartApiController {

    private final ChartService chartService;

    @GetMapping("/charts/{id}/sales")
    public ResponseDto<Integer> salesChart(@PathVariable Long id) {
        chartService.getSalesChart(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/charts/{id}/revisit")
    public ResponseDto<Integer> reVisitChart(@PathVariable Long id) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/charts/{id}/order-count")
    public ResponseDto<Integer> orderCountChart(@PathVariable Long id) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/charts/{id}/order-total")
    public ResponseDto<Integer> orderTotalChart(@PathVariable Long id) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/charts/{id}/reviews")
    public ResponseDto<Integer> reviewChart(@PathVariable Long id) {
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}
