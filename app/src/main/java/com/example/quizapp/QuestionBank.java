package com.example.quizapp;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {

    /**
     * Returns a shuffled list of all 50 questions.
     * Call this once in QuizActivity to load the game.
     */
    public static ArrayList<Question> getAllQuestions() {

        ArrayList<Question> list = new ArrayList<>();

        // ════════════════════════════════════════════════
        // CATEGORY 1 — General Knowledge (Q1–Q10)
        // ════════════════════════════════════════════════

        list.add(new Question(
                "How many continents are there on Earth?",
                new String[]{"5", "6", "7", "8"},
                2));  // C = 7

        list.add(new Question(
                "What is the largest ocean on Earth?",
                new String[]{"Atlantic", "Indian", "Arctic", "Pacific"},
                3));  // D = Pacific

        list.add(new Question(
                "How many sides does a hexagon have?",
                new String[]{"5", "6", "7", "8"},
                1));  // B = 6

        list.add(new Question(
                "Which planet is known as the Red Planet?",
                new String[]{"Venus", "Jupiter", "Mars", "Saturn"},
                2));  // C = Mars

        list.add(new Question(
                "What is the chemical symbol for water?",
                new String[]{"WA", "H2O", "HO2", "OW"},
                1));  // B = H2O

        list.add(new Question(
                "How many hours are in two days?",
                new String[]{"24", "36", "48", "72"},
                2));  // C = 48

        list.add(new Question(
                "What is the fastest land animal?",
                new String[]{"Lion", "Horse", "Cheetah", "Leopard"},
                2));  // C = Cheetah

        list.add(new Question(
                "How many players are on a standard football team?",
                new String[]{"9", "10", "11", "12"},
                2));  // C = 11

        list.add(new Question(
                "Which gas do plants absorb from the atmosphere?",
                new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"},
                2));  // C = CO2

        list.add(new Question(
                "What is the hardest natural substance on Earth?",
                new String[]{"Gold", "Iron", "Diamond", "Quartz"},
                2));  // C = Diamond

        // ════════════════════════════════════════════════
        // CATEGORY 2 — Science (Q11–Q20)
        // ════════════════════════════════════════════════

        list.add(new Question(
                "What is the powerhouse of the cell?",
                new String[]{"Nucleus", "Ribosome", "Mitochondria", "Vacuole"},
                2));  // C

        list.add(new Question(
                "What is the speed of light (approx) in km/s?",
                new String[]{"150,000", "300,000", "450,000", "600,000"},
                1));  // B

        list.add(new Question(
                "How many bones are in the adult human body?",
                new String[]{"180", "196", "206", "216"},
                2));  // C = 206

        list.add(new Question(
                "What force keeps planets in orbit around the Sun?",
                new String[]{"Magnetism", "Gravity", "Friction", "Nuclear force"},
                1));  // B

        list.add(new Question(
                "What is the atomic number of Carbon?",
                new String[]{"4", "6", "8", "12"},
                1));  // B = 6

        list.add(new Question(
                "Which planet has the most moons?",
                new String[]{"Jupiter", "Saturn", "Uranus", "Neptune"},
                1));  // B = Saturn (95 moons)

        list.add(new Question(
                "What is the most abundant gas in Earth's atmosphere?",
                new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Argon"},
                2));  // C = Nitrogen 78%

        list.add(new Question(
                "What is the unit of electrical resistance?",
                new String[]{"Volt", "Ampere", "Watt", "Ohm"},
                3));  // D = Ohm

        list.add(new Question(
                "DNA stands for?",
                new String[]{
                        "Deoxyribose Nucleic Acid",
                        "Dynamic Nucleic Acid",
                        "Deoxyribonucleic Acid",
                        "Double Nucleic Acid"},
                2));  // C

        list.add(new Question(
                "What is the boiling point of water at sea level (°C)?",
                new String[]{"90", "95", "100", "105"},
                2));  // C = 100

        // ════════════════════════════════════════════════
        // CATEGORY 3 — Geography (Q21–Q30)
        // ════════════════════════════════════════════════

        list.add(new Question(
                "What is the capital of France?",
                new String[]{"Lyon", "Marseille", "Paris", "Bordeaux"},
                2));  // C

        list.add(new Question(
                "Which is the longest river in the world?",
                new String[]{"Amazon", "Yangtze", "Mississippi", "Nile"},
                3));  // D = Nile

        list.add(new Question(
                "Which country has the largest land area?",
                new String[]{"USA", "China", "Canada", "Russia"},
                3));  // D = Russia

        list.add(new Question(
                "What is the capital of Japan?",
                new String[]{"Osaka", "Kyoto", "Tokyo", "Hiroshima"},
                2));  // C

        list.add(new Question(
                "On which continent is the Sahara Desert located?",
                new String[]{"Asia", "Australia", "South America", "Africa"},
                3));  // D

        list.add(new Question(
                "Which country is home to the Great Barrier Reef?",
                new String[]{"USA", "Brazil", "Australia", "Indonesia"},
                2));  // C

        list.add(new Question(
                "What is the capital of Morocco?",
                new String[]{"Casablanca", "Marrakech", "Rabat", "Fes"},
                2));  // C = Rabat

        list.add(new Question(
                "Which mountain is the highest on Earth?",
                new String[]{"K2", "Kangchenjunga", "Everest", "Lhotse"},
                2));  // C

        list.add(new Question(
                "How many countries are in Africa?",
                new String[]{"44", "54", "64", "74"},
                1));  // B = 54

        list.add(new Question(
                "Which ocean lies between Africa and Australia?",
                new String[]{"Atlantic", "Pacific", "Indian", "Arctic"},
                2));  // C = Indian

        // ════════════════════════════════════════════════
        // CATEGORY 4 — History (Q31–Q40)
        // ════════════════════════════════════════════════

        list.add(new Question(
                "In what year did World War II end?",
                new String[]{"1943", "1944", "1945", "1946"},
                2));  // C = 1945

        list.add(new Question(
                "Who was the first person to walk on the Moon?",
                new String[]{"Buzz Aldrin", "Yuri Gagarin", "Neil Armstrong", "John Glenn"},
                2));  // C

        list.add(new Question(
                "The Great Wall of China was primarily built to defend against whom?",
                new String[]{"Japanese", "Mongols", "Russians", "Persians"},
                1));  // B

        list.add(new Question(
                "In which year did the French Revolution begin?",
                new String[]{"1769", "1779", "1789", "1799"},
                2));  // C = 1789

        list.add(new Question(
                "Who invented the telephone?",
                new String[]{"Thomas Edison", "Nikola Tesla", "Alexander Graham Bell", "Marconi"},
                2));  // C

        list.add(new Question(
                "Which empire was ruled by Julius Caesar?",
                new String[]{"Greek", "Ottoman", "Roman", "Persian"},
                2));  // C

        list.add(new Question(
                "In what year did Morocco gain independence from France?",
                new String[]{"1952", "1956", "1960", "1962"},
                1));  // B = 1956

        list.add(new Question(
                "Who wrote the Declaration of Independence?",
                new String[]{"George Washington", "Benjamin Franklin", "Thomas Jefferson", "John Adams"},
                2));  // C

        list.add(new Question(
                "The Berlin Wall fell in which year?",
                new String[]{"1987", "1988", "1989", "1990"},
                2));  // C = 1989

        list.add(new Question(
                "Which ancient wonder was located in Alexandria?",
                new String[]{"Colossus of Rhodes", "Lighthouse of Alexandria", "Hanging Gardens", "Statue of Zeus"},
                1));  // B

        // ════════════════════════════════════════════════
        // CATEGORY 5 — Technology (Q41–Q50)
        // ════════════════════════════════════════════════

        list.add(new Question(
                "What does CPU stand for?",
                new String[]{"Central Process Unit", "Core Processing Unit", "Central Processing Unit", "Computer Processing Unit"},
                2));  // C

        list.add(new Question(
                "Which company created the Android operating system?",
                new String[]{"Apple", "Microsoft", "Google", "Samsung"},
                2));  // C

        list.add(new Question(
                "What does HTML stand for?",
                new String[]{
                        "Hyper Text Markup Language",
                        "High Text Machine Language",
                        "Hyper Transfer Markup Language",
                        "Hyper Text Modern Language"},
                0));  // A

        list.add(new Question(
                "How many bits are in one byte?",
                new String[]{"4", "8", "16", "32"},
                1));  // B = 8

        list.add(new Question(
                "Which programming language is primarily used for Android development?",
                new String[]{"Swift", "Python", "Java", "C++"},
                2));  // C = Java (and Kotlin)

        list.add(new Question(
                "What does 'www' stand for in a website URL?",
                new String[]{"World Wide Web", "Web World Wide", "Wide World Web", "World Web Wide"},
                0));  // A

        list.add(new Question(
                "Which data structure works on LIFO principle?",
                new String[]{"Queue", "Array", "Stack", "LinkedList"},
                2));  // C = Stack

        list.add(new Question(
                "What does SQL stand for?",
                new String[]{"Structured Query Language", "Simple Query Language", "Standard Query Logic", "System Query Language"},
                0));  // A

        list.add(new Question(
                "Which symbol is used for single-line comments in Java?",
                new String[]{"#", "--", "/*", "//"},
                3));  // D = //

        list.add(new Question(
                "What is the file extension for a Java source file?",
                new String[]{".jav", ".class", ".java", ".jv"},
                2));  // C = .java

        // ── shuffle so order is random every game ────────
        Collections.shuffle(list);

        return list;
    }
}