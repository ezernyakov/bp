# language: ru

@Clients
Функционал: Создание и редактирование клиентов

  Контекст:
    Дано на сервере нет клиентов с идентификатором:
      | 12347       |
      | id12347     |
      | id-12347.56 |
      | -.123       |
      | 12345       |
      | 123488      |
      | 123489      |
      | 123490      |

    И на сервере заведены клиенты
      | lastName | firstName | middleName    | id     |
      | Андреев  | Андрей    | Андреевич     | 12345  |
      | Сергеев  | Александр | Петрович      | 123488 |
      | Иванов   | Вадим     | Александрович | 123489 |
      | Fedorov  | Andrew    |               | 123490 |

    Допустим Администратор авторизуется на сервере


  Структура сценария: Создание нового клиента
    Когда Администратор создает нового клиента
      | lastName   | firstName   | middleName   | id   |
      | <lastName> | <firstName> | <middleName> | <id> |
    Тогда в системе есть клиент
      | lastName   | firstName   | middleName   | id   |
      | <lastName> | <firstName> | <middleName> | <id> |

    Примеры:
      | lastName  | firstName | middleName  | id          |
      | Антонов   | Антон     | Петрович    | 12347       |
      | Антонов   | Антон     |             | id12347     |
      | Иванов23  | Андрей23  | Василич23   | id-12347.56 |
      | 23Sidorov | 23Petr    | 23petrovich | -.123       |


  Структура сценария: Редактирование клиента
    Когда Администратор редактирует клиента
      | lastName   | firstName   | middleName   | id   |
      | <lastName> | <firstName> | <middleName> | <id> |
    Тогда в системе есть клиент
      | lastName   | firstName   | middleName   | id   |
      | <lastName> | <firstName> | <middleName> | <id> |

    Примеры:
      | lastName | firstName | middleName | id     |
      | Иванов   | Иван      | Иванович   | 12345  |
      | Сергеев  | Александр |            | 123488 |
      | Иванов   | Вадим     | петрович   | 123489 |
      | Fedorov  | Andrew    | Иванович   | 123490 |
