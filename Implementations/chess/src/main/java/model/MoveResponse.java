package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MoveResponse {
    private final boolean canMove;
    private final boolean createsCheck;
}
