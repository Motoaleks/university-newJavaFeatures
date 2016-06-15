package Main.Models.Work.Tasks;

import Main.Models.Data.Alcohol;
import Main.Models.Data.AlcoholContainer;
import Main.Models.Work.Task;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Aleksand Smilyanskiy on 15.06.2016.
 * "The more we do, the more we can do." ©
 */
public class FindValues extends Task<AlcoholContainer> {
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
        public Predicate<Alcohol> getPredicate(Alcohol lower, Alcohol higher) {
            switch (parameterId) {
                case 0: {
                    return (o1) -> (o1.className.compareTo(lower.className) == 0 && o1.className.compareTo(higher.className) == 0) ||
                            (o1.className.compareTo(lower.className) == 1 && o1.className.compareTo(higher.className) == -1);
                }
                case 1: {
                    return (o1) -> (o1.name.compareTo(lower.name) == 0 && o1.name.compareTo(higher.name) == 0) ||
                            (o1.name.compareTo(lower.name) == 1 && o1.name.compareTo(higher.name) == -1);
                }
                case 2: {
                    return (o1) -> (o1.id.compareTo(lower.id) == 0 && o1.id.compareTo(higher.id) == 0) ||
                            (o1.id.compareTo(lower.id) == 1 && o1.id.compareTo(higher.id) == -1);
                }
                case 3: {
                    return (o1) -> o1.litres >= lower.litres && o1.litres <= higher.litres;
                }
                case 4: {
                    return (o1) -> o1.price >= lower.price && o1.price <= higher.price;
                }
                case 5: {
                    return (o1) -> (o1.from.compareTo(lower.from) == 0 && o1.from.compareTo(higher.from) == 0) ||
                            (o1.from.compareTo(lower.from) == 1 && o1.from.compareTo(higher.from) == -1);
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

        public Alcohol getAlcohol(Object object){
            switch (parameterId) {
                case 0: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.className = (String) object;
                    return alcohol;
                }
                case 1: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.name = (String) object;
                    return alcohol;
                }
                case 2: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.id = (String) object;
                    return alcohol;
                }
                case 3: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.litres = (Integer) object;
                    return alcohol;
                }
                case 4: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.price = (Integer) object;
                    return alcohol;
                }
                case 5: {
                    Alcohol alcohol = new Alcohol();
                    alcohol.from = (String) object;
                    return alcohol;
                }
                default:
                    return null;
            }
        }
    }

    /**
     * Параметр по которому сортируется
     */
    private FindValues.Parameter parameter;
    /**
     * Контейнер для сортировки
     */
    private AlcoholContainer before;
    /**
     * После сортировки (если результаты разные, записываются друг за другом)
     */
    private Deque<AlcoholContainer> after = new LinkedList<>();

    private Alcohol lower;
    private Alcohol higher;

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


    public FindValues(AlcoholContainer alcoholContainer, Parameter parameter, Alcohol lower, Alcohol higher) {
        super("Find values in" + parameter.toString());
        before = alcoholContainer;
        this.parameter = parameter;
        this.lower = lower;
        this.higher = higher;
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
        if (lower == null || higher == null)
            return;
        Predicate<Alcohol> by = parameter.getPredicate(lower,higher);


        Stream<Alcohol> stream = before.getStorage().stream();
        AlcoholContainer resulted = stream.filter(by).collect(
                AlcoholContainer::new,
                AlcoholContainer::addInfo,
                AlcoholContainer::concatContainer
        );

        addResult(resulted);
    }

    @Override
    protected void doParallel() {
        if (lower == null || higher == null)
            return;
        Predicate<Alcohol> by = parameter.getPredicate(lower,higher);


        Stream<Alcohol> stream = before.getStorage().stream();
        AlcoholContainer resulted = stream.parallel().filter(by).collect(
                AlcoholContainer::new,
                AlcoholContainer::addInfo,
                AlcoholContainer::concatContainer
        );

        addResult(resulted);
    }

    @Override
    protected void doJavaOld() {
        Vector<Alcohol> alcohols = (Vector<Alcohol>) before.getStorage().clone();
        Vector<Alcohol> newVec = new Vector<>();
        Predicate<Alcohol> by = parameter.getPredicate(lower,higher);
        for (Alcohol alcohol:alcohols){
            if (by.test(alcohol)){
                newVec.add(alcohol);
            }
        }
        AlcoholContainer resulted = new AlcoholContainer(newVec);
        addResult(resulted);
    }

    @Override
    protected void doOther() {
        String not_ready = "Fork/join framework sort is not implemented";
        info.insert(0,not_ready).insert(not_ready.length(),"\n");
    }
}
