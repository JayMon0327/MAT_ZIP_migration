package MATZIP_ver3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesChartDto {
    private List<Map<String, Object>> dataPoints;
    private double thisMonthTotal;
    private double lastMonthTotal;
    private double twoMonthsAgoTotal;
    private double thisMonthDays;
    private double lastMonthDays;
    private double twoMonthDays;

}
