# Java Stream
## Stream
map reduce 연산 대상

### Stream 특징
Read Only
Can’t be Reused
Lazy Invocation (terminal operation 처리시 수행)

### Stream 종류
Stream<T> // 참조형 Stream
IntStream, LongStream, DoubleStream // 기본 자료형 Stream, 성능 이점 및 관련 연산자

### Stream 병렬 처리
stream.parallel() // 병렬 처리, 순서 보장X
stream.sequential() // default, 직렬 처리

### Stream 생성 방법
Stream.of(…)
Stream.iterate(0, n -> n + 1) // 무한 스트림 생성
Stream.generate(Math::random) // 무한 스트림 생성
Array.stream(T[] array) // 기본형 스트림 생성 가능
list.stream()
new Random().ints() // 유/무한 스트림 생성 가능
IntStream.range(1, 3) // 1, 2
IntStream.rangeClosed(1, 3) // 1, 2, 3
Files.list(Path dir) // 파일/디렉토리 목록
Files.lines(Path path) // 파일 내용
Stream.empty() // 빈 스트림

---

## Stream 연산
intermediate : filter, distinct, sort, limit, …
terminal : forEach, reduce, …

### Stream pipeline
intermediate stream 연산의 결과가 stream 여러번 반복적으로 연산 수행 가능
terminal 연산 수행은 단 한번만 가능. 수행 후에는 stream 재사용 불가

### Stream Intermediate 연산
distinct() // 중복 제거
filter(Predicate) // 조건 필터링
limit(long) // 자르기
skip(long) // 앞에 제외
peek(Consumer) // 사용만하기
sorted() // 정렬
sorted(Comparator) / 기준 추가 정렬
map(Function) // 변환
mapToDouble(ToDoubleFunction) // 변환 결과가 DoubleStream
mapToInt(ToIntFunction) // 변환 결과가 IntStream
mapToLong(ToLongFunction) // 변환 결과가 LongStream
  ex) map(i -> 1) // Stream<Integer>
         mapToInt(i -> 1) // 기본형 스트림 IntStream 으로 변환
flatMap(Function<T, Stream<R>>) // 변환, 변환 결과가 stream의 stream일 때 평탄화
flatMapToDouble()
flatMapToInt()
flatMapToLong()

### Stream Terminal 연산
forEach(Consumer) // 반복 처리
forEachOrdered(Consumer) // 반복 처리, 순서 유지
count() // 개수
Optional max(Comparator) // 가장 큰수
Optional min(Comparator) // 가장 작은 수
Optional findAny // 임의 한개
Optional findFirst // 첫번째
Object[] toArray() // 배열로 전환
reduce() // 리듀스 연산
collect() // 결과 처리

---

## Optional

Optional.of(…)
Optional.of(null) // NPE
Optional.ofNullable(null)

opt.isPresent() // 값있는지 확인
opt.ifPresent(Consumer) // 값있을때 처리 로직

opt.get()
opt.orElse(T) // null일때 대신할 값
opt.orElseGet(Supplier) // null일때 supplier로 값 생성
opt.orElseThrow(Supplier) // null일때 오류 발생

기본 자료형을 위한 Optional
  OptionalInt
  OptionalDouble
  OptionalLong
  getAsInt(), getAsLong(), getAsDouble() 과 같은 메소드 제공

---

## Intermediate Operators

### 자르기
skip(long) // 앞 잘라내기
limit(long) // 뒤 잘라내기

### map
map(Function) // 변환

### flatMap
flatMap(Function<T, Stream<R>>) // 변환 후 평탄화

### filter
filter(Predicate) // 조건에 맞는 것만 남기기
distinct() // 중복 제거하기

### sort
sorted() // 스트립 타입의 comparable로 정렬
sorted(Comparator) // 정렬 기준 제공

Comparator.comparing(Function keyExtractor) // 정렬 기준 값 제공
Comparator.comparing(Function keyExtractor, Comparator keyComparator) // 기준 값 및 비교 로직 제공
thenComparing 을 통해 추가 정렬 기준 제공 가능

### peek
peek(Consumer) // 소비만

---

## Terminal Operators

### forEach
void forEach(Consumer) // 순서 보장 X
void forEachOrdered(Consumer) // 순서 보장, 병렬

### reduce
Optional reduce(BinaryOperator acc)
T reduce(T identity, BinaryOperator acc)
U reduce(U identity, Bifunction<U,T,U> acc, BinaryOperator<U> combiner)

ex) int sum = intStream(0, (a, b) -> a + b)

### match
boolean allMatch(Predicate) // 모두 만족
boolean anyMatch(Predicate) // 하나라도 만족
boolean noneMatch(Predicate) // 모두 만족 X

### find
Optional findFirst() // 순차, 첫번째
Optional findAny() // 병렬, 아무거나 하나


### collect
Object collect(Collector collector)
Object collect(Supplier sup, BiConsumer acc, BiConsumer combiner)

interface Collector<T, A, R> {
  Supplier<A> supplier(); // 누적대상
  BiConsumer<A, T> accumulator(); // 누적
  BinaryOperator<A> combiner(); // 결합
  Function<A, R> finisher(); // 최종변환
  Set<Characteristics> characteristics(); // 컬렉터 특성 set
}

### Collectors
toList(), toSet(), toMap(), toCollection()
  ex) toCollection(ArrayList::new); toMap(p -> p.getKey(), p -> p.getValue());
toArray()
  ex) toArray(); /*object[]*/  toArray(Integer[]::new);
counting(), summingInt(), averagingInt(), maxBy(), minBy(), summarizingInt()
  ex) summingInt(User::getScore));
         maxBy(comparingInt(User::getScore));
joining(), reducing()

groupingBy(), partitioningBy(), collectingAndThen(), mapping()

### Grouping
Collector partitioningBy(Predicate) // Map<Boolean, List> 로 분리
Collector partitioningBy(Predicate, Collector) // Collector 결과에 따라 value 의 타입 정의

Collector groupingBy(Function classifier) // Map<T, List> 로 분리
Collector groupingBy(Function classifier, Collector downStream)
Collector groupingBy(Function classifier, Supplier mapFactory, Collector downStream)

