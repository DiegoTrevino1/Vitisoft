// package com.vitisoft.backend.service;

// import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.assertArrayEquals;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// class AccountManagerTest {

//     private AccountManager a;
//     DatabaseManager db = new DatabaseManager();
//     Account test = new Account("test", "1234", "test@gmail.com", "Ian", "Seymour");

//     @BeforeEach
//     void setUp() {
//         a = new AccountManager(db);
//         System.out.println("Initialized AccountManager.");
//     }

//     @AfterEach
//     void tearDown() {
//         a = null;
//         System.out.println("AccountManager toredown");
//     }

//     /*
//      * @description: test1_1 new account
//      */
//     @Test
//     @DisplayName("Test 1.1: Test new account.")
//     void test1_1(Account test) {
//         String userAns = "test";

//         String t = test.getUsername();

//         assertEquals(userAns, t, "Should return the same username if correctly created.");
//     }

//     /*
//      * @description: test2_1 test login
//      */
//     @Test
//     @DisplayName("Test 2.1: Test login.")
//     void test2_1(Account test) {
//         a.addAccount(test);

//         boolean ans = true;

//         assertEquals(ans, a.logIn("test", "1234"), "Login should return true.");
//     }

//     /*
//      * @description: test2_2 test logout
//      */
//     @Test
//     @DisplayName("Test 2.2: Test logout.")
//     void test2_2(Account test) {
//         a.addAccount(test);

//         String ans = null; // after logout, the current accoutn should be null

//         a.logIn("test", "1234");

//         a.logOut();

//         assertEquals(ans, a.getCurrentAccount(), "Both strings should be null after logout.");
//     }

//     /*
//      * @description: test2_3 Test password checking with 1234 hashed with a SHA 256 hash generator.
//      */
//     @Test
//     @DisplayName("Test 2.3: Test password checker/hash function.")
//     void test2_2() {
//         a.addAccount(test);

//         boolean ans = true; // should return true with correct hash and plain text combo  

//         assertEquals(ans, a.checkPassword("1234", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"), "The plain text pass and the hash should match and return true.");
//     }
// }
