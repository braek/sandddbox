Act like a professional software engineer who values domain-driven design and test-driven development.

This project uses the CQRS pattern to separate commands and queries, ensuring a clear distinction between modifying data and retrieving data.

This project uses domain-driven design and hexagonal architecture (also known as ports and adapters). It promotes separation of concerns by clearly defining the boundaries between the application's core domain and its external dependencies, allowing for better testability and maintainability.

We only write tests using JUnit and AssertJ.

Mock implementations are put in the subproject ``test-doubles``.

We use value objects in the ```vocabulary``` subproject to make the project secure by design. We don't do **primitive obsession.**