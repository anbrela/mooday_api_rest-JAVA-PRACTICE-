-- Comprobamos si la tabla `tags` está vacía
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM tags) THEN
INSERT INTO tags (name, lang, usageCount)
VALUES
    ('He hecho deporte', 'es', 0),
    ('I worked out', 'en', 0),
    ('He estado con amigos', 'es', 0),
    ('I spent time with friends', 'en', 0),
    ('Me siento bien', 'es', 0),
    ('I feel good', 'en', 0),
    ('He escuchado música', 'es', 0),
    ('I listened to music', 'en', 0),
    ('He cocinado', 'es', 0),
    ('I cooked', 'en', 0),
    ('He tenido la regla', 'es', 0),
    ('I had my period', 'en', 0),
    ('Me quedé sin dinero', 'es', 0),
    ('I ran out of money', 'en', 0),
    ('Me siento triste', 'es', 0),
    ('I feel sad', 'en', 0),
    ('He estudiado', 'es', 0),
    ('I studied', 'en', 0),
    ('Me siento cansado/a', 'es', 0),
    ('I feel tired', 'en', 0),
    ('He meditado', 'es', 0),
    ('I meditated', 'en', 0),
    ('He leído un libro', 'es', 0),
    ('I read a book', 'en', 0),
    ('Me siento estresado/a', 'es', 0),
    ('I feel stressed', 'en', 0),
    ('He pasado tiempo en familia', 'es', 0),
    ('I spent time with family', 'en', 0),
    ('He hecho algo creativo', 'es', 0),
    ('I did something creative', 'en', 0),
    ('He salido de viaje', 'es', 0),
    ('I went on a trip', 'en', 0),
    ('He trabajado', 'es', 0),
    ('I worked', 'en', 0),
    ('He visto una película', 'es', 0),
    ('I watched a movie', 'en', 0),
    ('He comido saludable', 'es', 0),
    ('I ate healthy', 'en', 0),
    ('Me siento enamorado/a', 'es', 0),
    ('I feel in love', 'en', 0);
END IF;
END $$;
