## 4.1.2. Разные типы разметки
Вы можете использовать один из следующих типов разметки:
1. ConstraintLayout - разметка привязки;
2. FrameLayout - разметка фрейма;
3. LinearLayout - линейная разметка;
4. TableLayout - табличная разметка;
5. GridLayout - разметка в виде сетки (устаревший тип разметки);
6. RеlаtivеLауоut - относительная разметка (устаревший тип разметки).

По умолчанию используется разметка constraintLayout. Вместе с ней можно задействовать направляющие линии - Guideline (рис. 4.3). Сами по себе направляющие линии не являются разметкой, и их можно использовать только вместе с constraintLayout. Направляющие линии бывают вертикальными и горизонтальными. Они помогают расположить элементы интерфейса более точно. Направляющие линии не отображаются на устройстве, а виды только в визуальном редакторе.
Аналогичные направляющие линии есть в Visual Studio, а в Android Studio их нужно добавлять самостоятельно.
![Img](https://github.com/DmitryTaran/MobileDevelopment/blob/main/1.png)
**Рис . 4.3. Направляющие линии**

Типы разметки GridLayout и RelativeLayout в приведенном ранее списке помечены как устаревшие, и сейчас - если они вам нужны - их можно найти в разделе Legacy. Разметка constraintLayout, как уже было отмечено, используется по умолчанию, но для полноты картины нужно рассмотреть и другие способы разметки, в том числе и устаревшие.

### Разметка FrameLayout
FrameLayout - самый простой тип разметки. Все дочерние элементы FrameLayout будут прикреплены к верхнему левому углу экрана. Этот тип разметки настолько примитивен, что даже самые простые приложения
ориентируются на другие типы разметки. Единственное применение для
FrameLayout - это ее использование внутри ячейки таблицы (см. далее), а для создания полноценной разметки приложения эта разметка не годится.

### Разметка LinearLayout

Линейная разметка (LinearLayout) размещает виджеты горизонтально или вертикально, в зависимости от атрибута android:orientation `android:orientation="vertical"`
Посмотрите на рис. 4.4 - на нем задана линейная вертикальная разметка, поэтому кнопки размещаются друг под другом - вертикально. Чтобы задать горизонтальную разметку, нужно изменить атрибут так:
`android:orientation="horizontal"`
В листинге 4.1 приведен файл разметки для интерфейса с двумя кнопками, показанногона рис. 4.4. Измените атрибут android:orientation, установив значение horizontal, чтобы получился такой код:
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="fill_parent"
android:layout_height="fill_parent"
android:orientation="horizontal" >
```
![Img](https://github.com/DmitryTaran/MobileDevelopment/blob/main/2.png)

**Рис. 4.4. Линейная разметка (см. листинг 4.1)**

Затем перейдите на вкладку Deisgn или воспользуйтесь областью Preview, и вы увидите, что наши кнопки выстроились горизонтально.
Но это еще не все. У разметки LinearLayout есть атрибут android:layout_weight, задающий «вес» отдельного дочернего элемента. Чем выше «вес», тем важнее элемент. Элемент с максимальным «весом» займет всю оставшуюся часть родительского элемента. По умолчанию вес элемента равен О. Установите для второй кнопки вес, отличный от О:
```xml
<Button
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_weight="l"
android:text="Stop"
android:id="@+id/button" />
```

В результате последняя кнопка будет растянута на всю оставшуюся ширину или высоту экрана - в зав·исимости от типа разметки (рис. 4.5).

![Img](https://github.com/DmitryTaran/MobileDevelopment/blob/main/3.png)

**Рис. 4.5. Кнопка растянута на всю оставшуюся ширину экрана (для случая горизонтальной разметки)**

### Разметка TableLayout
Разметка TableLayout размещает виджеты в строки и столбцы таблицы. Границы самой таблицы не отображаются, поскольку таблица здесь используется не для представления данных, а сугубо для размещения виджетов. Таблица может иметь строки с разным количеством ячеек. Для формирования строк таблицы разметки используются объекты TableRow. Каждый такой объект - это одна строка таблицы. В строке может вообще не быть ячеек, может быть одна или несколько ячеек. В свою очередь ячейка может быть объектом класса viewGroup- другими словами, ячейка допускает разметку. То есть, в ячейку таблицы вы можете поместить объект LinearLayout и с его помощью разместить элементы внутри ячейки. Можно таюке задействовать в качестве элемента ячейки другой объект тaЫeLayout - получится вложенная таблица.
Сейчас мы продемонстрируем использование табличной разметки на примере создания клавиатуры для простейшего калькулятора. Создайте новый проект или
откройте наш простой проект, разработанный в главе 2. Перейдите к редактору разметки (для этого откройте в структуре проекта файл `\res\layout\activity_main.xml` и щелкните на нем двойным щелчком). Затем перейдите на вкладку `activity_main.xrnl` и удалите из ХМL-файла все, кроме элемента `<?xml>`.
Теперь создайте разметку- для этого в группе Layouts выберите TaЬleLayout. В эту разметку требуется добавить четыре элемента таblеRоw. Для каждого элемента таblеRоw установите следующие атрибуты:
-  `Gravity = center;`
- ` Layout width = fill _parent;`
- ` Layout height = wrap _ content.`
Свойство (атрибут) Gravity, определение которого показано на рис. 4.6, задает выравнивание элемента в контейнере, - мы устанавливаем с его помощью выравнивание по центру: [center].

![Img](https://github.com/DmitryTaran/MobileDevelopment/blob/main/4.png)

**Рис. 4.6. Установка значения атрибута Gravity**

Далее в каждую строку (в каждый контейнер TableRow} надо добавить по четыре кнопки (кнопки находятся в группе Widgets} и установить ширину каждой кнопки в 20 pt (это значение зависит от типа платформы Android и от размера экрана). Наша клавиатура готова (рис. 4.7) - обратите внимание на иерархию графического интерфейса (Component Tree).

![Img](https://github.com/DmitryTaran/MobileDevelopment/blob/main/5.png)

**Рис. 4.7. Созданная клавиатура калькулятора**

В ХМL-файле разметка TableLayout будет объявлена так (листинг 4.2).

Листинг 4.2. Полный код XML-файла разметки клавиатуры калькулятора

```xml
<?xml version="l.O" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:id="@+id/TaЬleLayoutOl"
android:layout_width="fill_parent"
android:gravity="center"
android:layout_height="fill_parent">
<TableRow
android:id="@+id/TaЬleRowOl"
android:layout_height="wrap_content"
android:layout_width="fill_parent"
android:gravity="center">
	<Button
android:id="@+id/ButtonOl"
android:layout_height="wrap_content"
android:text="l"
android:layout_width="20pt"/>
<Button
android:id="@+id/Button02"
android:layout_height="wrap_content"
android:text="2"
android:layout_width="20pt"/>
<Button
android:id="@+id/Button03"
android:layout_height="wrap_content"
android:text="З"
android:layout_width="20pt"/>
<Button
android:id="@+id/ButtonPlus"
android:layout_height="wrap_content"
android:text="+"
android:layout_width="20pt"/>
</TaЫeRow>
<TableRow
android:id="@+id/TaЫeRow02"
android:layout_height="wrap_content"
android:layout_width="fill_parent"
android:gravity="center">
<Button
android:id="@+id/Button04"
android:layout_height="wrap_content"
android:layout_width="20pt"
android:text="4"/>
<Button
android:id="@+id/Button05"
android:layout_height="wrap_content"
android:layout_width="20pt"
android:text="S"/>
<Button
android:id="@+id/Button06"
android:layout_height="wrap_content"
android:layout_width="20pt"
android:text="б"/>
<Button
android:id="@+id/ButtonМinus"
android:layout_height="wrap_content"
android:text="-"
android:layout_width="20pt"/>
</TableRow>
```
