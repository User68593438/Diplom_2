package model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Создать заказ
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private List<String> ingredients;

}
