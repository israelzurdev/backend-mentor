-- Reto 1: Autenticación básica (JUNIOR + SECURITY)
INSERT INTO challenges (title, slug, difficulty, category, description_md)
VALUES ('Servidor de Autenticación JWT',
        'crear-servidor-auth',
        'JUNIOR',
        'SECURITY',
        '# Enunciado del reto\n\nConstruye un endpoint `/auth/login` que reciba credenciales y devuelva un token JWT firmado.');

-- Reto 2: Optimización de consultas (MID + DATABASE)
INSERT INTO challenges (title, slug, difficulty, category, description_md)
VALUES ('Optimización de Consultas e Índices',
        'optimizacion-consultas-indices',
        'MID',
        'DATABASE',
        '# Enunciado del reto\n\nAnaliza una consulta lenta con `EXPLAIN ANALYZE` y crea el índice compuesto adecuado para reducir el tiempo de respuesta a menos de 5ms.');
