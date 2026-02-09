package com.agrosense.backend.dto.cropguide;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuidelinesDto {
    private List<String> dos;
    private List<String> donts;
}
