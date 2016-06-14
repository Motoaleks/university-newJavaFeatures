package Main.Models.Work.Tasks;

import Main.Models.Data.Alcohol;
import Main.Models.Data.AlcoholContainer;
import Main.Models.Work.Task;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */

/**
 * Сортировка алкогольной продукции по одному из параметров
 */
public class Sort extends Task<AlcoholContainer> {
    /**
     * Параметры по которым сортируется (колонки)
     */
    public enum Parameter {
        /**
         * {@link Alcohol#className}
         */
        Class(0),
        /**
         * {@link Alcohol#name}
         */
        Name(1),
        /**
         * {@link Alcohol#id}
         */
        ID(2),
        /**
         * {@link Alcohol#litres}
         */
        Litres(3),
        /**
         * {@link Alcohol#price}
         */
        Price(4),
        /**
         * {@link Alcohol#from}
         */
        Country(5);

        /**
         * Внутренний номер члена перечисления
         */
        int parameterId = -1;

        /**
         * Параметр по которому сортируется
         *
         * @param parameterId порядковый номер
         */
        Parameter(int parameterId) {
            this.parameterId = parameterId;
        }

        /**
         * Получение сравнителя для данного параметра
         *
         * @return Comparator по выбранному параметру
         */
        public Comparator<Alcohol> getComparator() {
            switch (parameterId) {
                case 0: {
                    return (o1, o2) -> o1.className.compareTo(o2.className);
                }
                case 1: {
                    return (o1, o2) -> o1.name.compareTo(o2.name);
                }
                case 2: {
                    return (o1, o2) -> o1.id.compareTo(o2.id);
                }
                case 3: {
                    return (o1, o2) -> {
                        if (o1.litres < o2.litres) return -1;
                        if (o1.litres > o2.litres) return 1;
                        return 0;
                    };
                }
                case 4: {
                    return (o1, o2) -> {
                        if (o1.price < o2.price) return -1;
                        if (o1.price > o2.price) return 1;
                        return 0;
                    };
                }
                case 5: {
                    return (o1, o2) -> o1.from.compareTo(o2.from);
                }
                default:
                    return null;
            }
        }

        /**
         * ID параметра
         *
         * @return
         */
        public int getParameterId() {
            return parameterId;
        }

        /**
         * Получение имени параметра
         *
         * @return имя параметра
         */
        public String toString() {
            switch (parameterId) {
                case 0: {
                    return "class";
                }
                case 1: {
                    return "name";
                }
                case 2: {
                    return "id";
                }
                case 3: {
                    return "litres";
                }
                case 4: {
                    return "price";
                }
                case 5: {
                    return "from country";
                }
                default: {
                    return "undefined";
                }
            }
        }
    }

    /**
     * Параметр по которому сортируется
     */
    private Parameter parameter;
    /**
     * Контейнер для сортировки
     */
    private AlcoholContainer before;
    /**
     * После сортировки (если результаты разные, записываются друг за другом)
     */
    private Deque<AlcoholContainer> after = new LinkedList<>();

    /**
     * Добавление нового результата сортировки, если сортировка дала такой же результат - новый не добавляется
     *
     * @param alcoholContainer результат сортировки
     */
    private void addResult(AlcoholContainer alcoholContainer) {
        if (after.size() == 0) {
            after.add(alcoholContainer);
            return;
        }
        if (!after.contains(alcoholContainer)) {
            after.add(alcoholContainer);
        }
    }

    /**
     * Создание задачи - сортировка
     *
     * @param alcoholContainer Контейнер для сортировки
     * @param parameter        {@link Sort#parameter}
     */
    public Sort(AlcoholContainer alcoholContainer, Parameter parameter) {
        super("Sorting values by " + parameter.toString());
        this.parameter = parameter;
        before = alcoholContainer;
    }

    @Override
    public AlcoholContainer getDataBefore() {
        return before;
    }

    @Override
    public AlcoholContainer getDataAfter() {
        if (ready())
            return after.getFirst();
        else
            return null;
    }

    @Override
    public boolean sameOutput() {
        if (after.size() > 1) {
            return false;
        }
        return true;
    }

    @Override
    protected void doSimple() {
        Comparator<Alcohol> by = parameter.getComparator();

        // 7., 8. Ссылки на методы/конструкторы
        Stream<Alcohol> stream = before.getStorage().stream();
        AlcoholContainer resulted = stream.sorted(by).collect(
                AlcoholContainer::new,
                AlcoholContainer::addInfo,
                AlcoholContainer::concatContainer
        );

        addResult(resulted);
    }

    @Override
    protected void doParallel() {
        Comparator<Alcohol> by = parameter.getComparator();

        Stream<Alcohol> stream = before.getStorage().stream();
        AlcoholContainer resulted = stream.parallel().collect(
                AlcoholContainer::new,
                AlcoholContainer::addInfo,
                AlcoholContainer::concatContainer
        );

        addResult(resulted);
    }

    @Override
    protected void doJavaOld() {
        Vector<Alcohol> alcohols = (Vector<Alcohol>) before.getStorage().clone();
        Collections.sort(alcohols, parameter.getComparator());
        AlcoholContainer resulted = new AlcoholContainer(alcohols);
        addResult(resulted);
    }

    @Override
    protected void doOther() {
        String not_ready = "Fork/join framework sort is not implemented";
        info.insert(0,not_ready).insert(not_ready.length(),"\n");
    }
}
