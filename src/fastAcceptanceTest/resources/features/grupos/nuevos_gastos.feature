# language: es

Característica: sumar gastos nuevos a un grupo existente

  Regla: La suma total de gastos de un grupo no puede ser menor a cero
    @wip
    Escenario: No puedo sumar un gasto menor a cero a un saldo cero
      Dado que existe un grupo con saldo en 0
      Cuando uno de sus usuarios intenta sumar un monto de -5
      Entonces no se suma el saldo al grupo
      Y el total debe permanecer en 0