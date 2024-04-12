package MATZIP_ver3.service;

import MATZIP_ver3.repository.ChartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChartService {

    private final ChartRepository chartRepository;

    @Transactional
    public void getSalesChart(Long id) {
    }
}
