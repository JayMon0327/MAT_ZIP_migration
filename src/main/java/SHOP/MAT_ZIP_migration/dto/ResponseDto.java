package SHOP.MAT_ZIP_migration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    int status;
    T data;
    Long id;

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
