# examenMELI

A continuación se dan las indicaciones para consumir el servicio para la consulta de secuencias de ADN con el fin de determinar si un humano es mutante o no.

Costrucción

El desarrollo se realizo con lenguaje Java, se contruyo con la herramienta de AWS para la creacion de proyectos lambda.

Se creao un APIGateway publico en aws con el fin de consumir esta lambda, el API tiene dos metodos, el primero se utiliza para enviar la secuencia a analizar,
este retorna 200 cuando es mutante o 403 en caso de que no lo sea.

el endpoint para este servicio es:

 https://u1y5ong0sl.execute-api.us-east-1.amazonaws.com/mutants/mutants
 
 trama de consumo:
 
 {
    "dna":["CTGCGA","CATTGC","ATATGT","CGAATG","CCACTA","TCACTG"]
}

El segundo metodo realiza un porcentaje corespondiente a cuantas de las cadenas consultadas, son mutantes:

El endpoint de dicho servicio es:

 https://u1y5ong0sl.execute-api.us-east-1.amazonaws.com/mutants/stats
 
 { “count_mutant_dna”:40, 
   “count_human_dna”:100 
   “ratio”:0.4
   }
