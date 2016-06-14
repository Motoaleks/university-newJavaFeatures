package Main.Models.Data;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */

import Main.Models.MessageManager;

/**
 * Загружатель алкоголя из файла и возвращение. Интерфейс просто потому что надо static. Лучше было бы сделать класс.
 */
public interface AlcoholContainerLoader {
    /**
     * Загрузка Алкоголя из файля
     * @return контейнер с информацией об алкогольной продукции
     */
    static AlcoholContainer loadFromResourses(){
       return new AlcoholContainer(AlcoholContainer.class.getResource("../../resources/data.csv").getPath().replaceFirst("/",""));
    }
}
