package com.mycompany.tfm_juan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona las preguntas del juego AQUILAE
 */
public class QuestionManager {
    
    public static class Question {
        String question;
        String answer;
        
        public Question(String q, String a) {
            this.question = q;
            this.answer = a;
        }
        
        public String getQuestion() {
            return question;
        }
        
        public String getAnswer() {
            return answer;
        }
    }
    
    private static final Question[] ALL_QUESTIONS = {
        //NO SE PUEDE PONER COMILLAS DOBLES EN UNA PREGUNTA ( "" ), HAY QUE PONER SIMPLES ('') -- se pueden poner doble comilla simple pero sino detecta que acaba la pregunta y dará error
        new Question("¿Qué héroe es considerado el progenitor del pueblo romano?", "Eneas"),
        new Question("¿De qué ciudad provenía Eneas?", "De Troya"),
        new Question("¿Quién fue el primer rey de Roma?", "Rómulo")
    };
    
    // Lista de preguntas disponibles (se irá reduciendo conforme se usen)
    private static List<Question> availableQuestions = new ArrayList<>();
    
    // Inicializar la lista de preguntas disponibles
    static {
        resetQuestions();
    }
    
    /**
     * Obtiene una pregunta aleatoria que no se haya usado aún
     * @return Una pregunta aleatoria, o null si se acabaron las preguntas
     */
    public static Question getRandomQuestion() {
        if (availableQuestions.isEmpty()) {
            return null; // O podrías resetear las preguntas automáticamente
        }
        
        // Tomar una pregunta aleatoria y eliminarla de las disponibles
        int randomIndex = (int)(Math.random() * availableQuestions.size());
        return availableQuestions.remove(randomIndex);
    }
    
    /**
     * Reinicia todas las preguntas para que vuelvan a estar disponibles
     */
    public static void resetQuestions() {
        availableQuestions.clear();
        Collections.addAll(availableQuestions, ALL_QUESTIONS);
    }
    
    /**
     * Obtiene el número de preguntas disponibles
     * @return Número de preguntas que aún no se han usado
     */
    public static int getAvailableQuestionsCount() {
        return availableQuestions.size();
    }
    
    /**
     * Obtiene el número total de preguntas
     * @return Número total de preguntas en el sistema
     */
    public static int getTotalQuestionsCount() {
        return ALL_QUESTIONS.length;
    }
}