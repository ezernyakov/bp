# language: ru

@Clients
Функционал: Создание и редактирование клиентов

  Контекст:
    Дано на сервере нет клиентов с идентификатором:
      | 12345 |
      | 12346 |

    И на сервере заведены клиенты
      | lastName | firstName | middleName | id    |
      | Андреев  | Андрей    | Андреевич  | 12345 |
      | Антонов  | Иван      | Сергеевич  | 12346 |

    Допустим пользователь авторизуется на сервере как Администратор


  Сценарий: Создание нового кассира
    Когда Администратор создает нового клиента
      | lastName | firstName | middleName | id    |
      | Антонов  | Антон     | Петрович   | 12347 |
    Тогда в списке есть клиент с идентификатором '12347'

  Сценарий: Редактирование клиента
    Когда Администратор редактирует клиента с идентификатором '12345'
      | lastName | firstName | middleName | id    |
      | Сергеев  | Александр | Петрович   | 12345 |
    Тогда в списке  клиентов есть клиент
      | lastName | firstName | middleName | id    |
      | Сергеев  | Александр | Петрович   | 12345 |