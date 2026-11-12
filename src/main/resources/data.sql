-- Datos de ejemplo que se cargan al arrancar (base H2 en memoria).

-- Pólizas (ids 1 a 5 en este orden)
INSERT INTO polizas (numero_poliza, tipo, estado, tomador, canon, prima, meses_vigencia, fecha_inicio, fecha_fin)
VALUES ('POL-0001', 'INDIVIDUAL', 'VIGENTE', 'Carlos Pérez', 1500000.00, 18000000.00, 12, '2026-01-01', '2026-12-31');

INSERT INTO polizas (numero_poliza, tipo, estado, tomador, canon, prima, meses_vigencia, fecha_inicio, fecha_fin)
VALUES ('POL-0002', 'COLECTIVA', 'VIGENTE', 'Inmobiliaria Los Andes S.A.S.', 2000000.00, 24000000.00, 12, '2026-03-01', '2027-02-28');

INSERT INTO polizas (numero_poliza, tipo, estado, tomador, canon, prima, meses_vigencia, fecha_inicio, fecha_fin)
VALUES ('POL-0003', 'COLECTIVA', 'VIGENTE', 'Administración Conjunto Torres del Parque', 1200000.00, 7200000.00, 6, '2026-07-01', '2026-12-31');

INSERT INTO polizas (numero_poliza, tipo, estado, tomador, canon, prima, meses_vigencia, fecha_inicio, fecha_fin)
VALUES ('POL-0004', 'INDIVIDUAL', 'RENOVADA', 'Laura Gómez', 1050000.00, 12600000.00, 12, '2026-02-01', '2027-01-31');

INSERT INTO polizas (numero_poliza, tipo, estado, tomador, canon, prima, meses_vigencia, fecha_inicio, fecha_fin, fecha_cancelacion)
VALUES ('POL-0005', 'COLECTIVA', 'CANCELADA', 'Inmobiliaria El Prado Ltda.', 1800000.00, 21600000.00, 12, '2026-01-15', '2027-01-14', '2026-06-30');

-- Riesgos (el riesgo se relaciona con la póliza por su número, para no depender de ids fijos)
INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0001'), 'Calle 45 # 12-30 Apto 301, Bogotá', 'Carlos Pérez', 'Martha Rodríguez', 'ACTIVO', '2026-01-01');

INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0002'), 'Carrera 7 # 80-15 Apto 502, Bogotá', 'Andrés Castro', 'Jorge Méndez', 'ACTIVO', '2026-03-01');

INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0002'), 'Calle 100 # 15-20 Of 204, Bogotá', 'Diana Torres', 'Inversiones Norte S.A.S.', 'ACTIVO', '2026-03-01');

INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0003'), 'Torre 2 Apto 1104, Torres del Parque, Bogotá', 'Felipe Ríos', 'Ana Lucía Vargas', 'ACTIVO', '2026-07-01');

INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0004'), 'Avenida 68 # 22-10 Casa 5, Bogotá', 'Laura Gómez', 'Pedro Salazar', 'ACTIVO', '2026-02-01');

INSERT INTO riesgos (poliza_id, direccion_inmueble, arrendatario, arrendador, estado, fecha_inclusion, fecha_cancelacion)
VALUES ((SELECT id FROM polizas WHERE numero_poliza = 'POL-0005'), 'Calle 72 # 9-40 Apto 201, Bogotá', 'Sofía Herrera', 'Luis Fernando Ortiz', 'CANCELADO', '2026-01-15', '2026-06-30');
