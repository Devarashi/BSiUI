## Zerówka
* zad 1 - Wchodząc w konsole developera znalazłem komentarz w html pod formularzem o zawartości ROZWAL_{DontMessWithZohan}
* zad 5 - Wchodząc w konsole developera, znlazłem, że po na ciśnięciu przycisku wywoływana jest metoda JS o nazwie 
checkPassword() w której ciele znalazłem alert('ROZWAL_{ILikeBiscuits}');
Można to wyświetlić wpisując w formularzu hasło "realpassword"
* zad 2 - W headerach jest pole Flag: ROZWAL_DontMessWithZohan2}
* zad 3 - w polu password jest pisana wartość "adeabecc", dostajemy również header "xor: 12341234" i po zxorowaniu tych dwóch wartości (jako hexadecymalne) dostajemy wartość "bfdeacf8", która jest hasłem i pozwala się zalogować. Flaga: ROZWAL_{NiceToXorYou}

## Sql
* zad 3 - doklejając do url ?id=2, dostałem flagę ROZWAL_{ZjazdGimboli}

## Crypto
* zad 3 - napisałem program, który xoruje tekst z kolejnymi kodami ascii i sprawdza czy tekst zawiera tekst 'ROZWAL' jeżeli tak to wyświetla zxorowany tekst i kończy działanie. Odnalazłem flagę ROZWAL_{SingleXorByteCipher}. Kod znajduje się w katalogu bob.

* zad 4 - analizując kod, zauważyłem, że flaga zostanie wyświetlona, gdy znajdziemy kolizję 2 hashy, dla dwóch pól wejściowych. W programie funkcja hashująca używa algorytmu md5, którym hashuje stringa ze stałym prefixem "Sx12s;!,.alxMausA_!s" oraz sufixem z danego pola z formularza, a następnie zwraca z tego jedynie pierwsze 64 bity. Znalazłem, że istnieje coś takiego jak chosen-prefix collision jednak nie udało mi się znaleźć do tego żadnej gotowej biblioteki.


* zad 6 - analizując częstość występowania liter w alfabecie angielskim (http://www.richkni.co.uk/php/crypta/freq.php), zauwazyłem, następujące zależonści: 'b' w tekście to najprawdopodobniej 't', 'd' to h, 's' to 'e'. Probowałem zxorować jednak nie przyniosło to rezultau, jak również probowałem szyfru cezara.
Odnalazlem cos co moze byc haslem poniewaz ma odpowiedni schemat KUWQJG_{CpzCblbpbluiTlfdskLcQsjr}
stąd wnioskuje:
k -> r,
u -> o,
w -> z,
q -> w,
j -> a,
g -> l

Sx12s;!,.alxMausA_!sa