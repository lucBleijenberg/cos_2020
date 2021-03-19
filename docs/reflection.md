
# Reflection

In dit bestand staan enkele issues die tijdens het project naar boven kwamen en hoe deze zijn opgelost.
Er wordt aangenomen dat de basis kennis van de taal al aanwezig is door `language.md` te hebben gelezen.  

In het bestand `scope_class_diagram.jpg` staat een classe diagram van de classes met betrekking tot de `Scope` en de symbolen die worden gebruikt om variabelen / methodes op te slaan.

#### Method generation

Tijdens het 'bezoeken' van een programma wordt er code gegenereerd voor de `main` methode en worden alle bytecode statements daar in gezet.  
Dit veroorzaakte een probleem voor het genereren van methodes, omdat die pas na de `main` methode moeten worden gegenereerd.  
Daarom houd de `CodeGenerator` nu een lijst bij van `Runnable`s, waar stukken code kunnen worden ingezet die later pas worden uitgevoerd.  
Alle (eventuele) `Runnable`s in deze lijst worden uitgevoerd nadat de `main` methode klaar is, waardoor het genereren van code van bijvoorbeeld methodes pas later word gedaan.

#### Method overloading

Omdat deze taal ook method overloading ondersteunt, konden methodes (MethodSymbol) niet worden vergeleken op naam zoal bij variabelen gebeurt.    
In plaats daarvan wordt de signature van de methode gebruikt om een methode op te zoeken.  
De signature die gebruikt wordt is het zelfde als de bytecode-signature zonder return type, dus de volgende methodes:

    void a {int a}
    int  a {int a} 
    void a {float a}  
    void a {int a, int b}
    
gebruiken de volgende signatures:

    a(I)
    a(I)
    a(F)
    a(IF)
    
Hierdoor is het mogelijk om meerdere methodes met de zelfde naam, maar met andere parameters te gebruiken.

#### Boolean expressies

Zoals in `language.md` beschreven staat, ondersteunt deze taal ook conditional-code in de vorm van when-statements (in feite `if else-if else-if else` statements).

In eerste instantie werden boolean expressies zoals `3 < 4` direct in een `if_icmp[type]` statement vertaald om code wel of niet uit te voeren.  
Dus de volgende code bijvoorbeeld:


    # 0/1 a = on  (eerder in code)
    
    when 
        {3 < 4}   [ ... ]
        {4 == 5}  [ ... ]
        {a}       [ ... ]
        otherwise [ ... ]

werd vertaalt tot de volgende bytecode:

    ldc 3
    ldc 4
    if_icmpge  EndCondition_0
        ; true code (3 < 4)
        goto EndWhen_0
    EndCondition_0:
    
    ldc 4
    ldc 5
    if_icmne EndCondition_1
        ; true code (4 == 5)
        goto EndWhen_1
    EndCondition_1:
    
    ; what to do for `a` ???
    
    ; otherwise code
    
    EndWhen_0:
    
Echter dit werd een probleem voor bijvoorbeeld `when {on} [...]` en `when {a} [...]`.  
Uiteindelijk werd dit probleem opgelost door simpelweg een `0` of een `1` op de stack te zetten,  
en altijd `if_eq` (is bovenste waarde op stack gelijk aan `0`) gebruikt in when statements, zoals in onderstaande code:

    ; put 0/1 on stack (3 < 4 ? 1 : 0)
    
    if_eq EndCondition_0
        ; true code (3 < 4)
        goto EndWhen_0
    EndCondition_0:
    
    ; put 0/1 on stack (4 == 5 ? 1 : 0)
    
    if_eq EndCondition_1 
        ; true code (4 == 5)
        goto EndWhen_1
    EndCondition_1:
    
    ; put 0/1 on stack (a ? 1 : 0)
    
    if_eq EndCondition_2
        ; true code (a is `on`)
        goto EndWhen_2
    EndCondition_2:
    
    ; otherwise code
    
    EndWhen_0:
    
Deze oplossing heeft ook als voordeel dat de code netter is, omdat een when statements alleen maar hoeft te verwachten dat er een `0` of `1` op de stack staat.  
Dit geld ook voor het opslaan van boolean waarden en het berekenen van complexe boolean expressies.