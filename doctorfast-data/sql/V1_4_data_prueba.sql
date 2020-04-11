INSERT INTO public.persona_usuario(
            id_persona_usuario, apellido_paterno, apellido_materno, nombre, 
            documento_identidad, id_perfil, usuario, clave, estado)
    VALUES (1, 'Zarate', 'Castañeda', 'Etmee', 
            '99999999', 1, 'ezarate', 'a49fc94d0d141f988e83df8a3a528db9', '1');

INSERT INTO public.clinica(
            id_clinica, ruc, nombre_comercial, nombre_abreviado, direccion_comercial, 
            logo, estado)
    VALUES (1, '20601400490', 'CENTRO ODONTOLOGICO ARTEETH S.A.C', 'Arteeth', 'Av. Primavera 609 - Oficina 507 San Borja, Lima', 
            '', '1');

INSERT INTO public.administrador(
            id_administrador, estado, id_clinica, id_persona_usuario)
    VALUES (1, '1', 1, 1);

INSERT INTO public.sede(
            id_sede, estado, 
            id_clinica, codigo, co_departamento, co_provincia, co_distrito, 
            nombre, direccion)
    VALUES (1, '1',
            1, 1, 15, 1, 30, 
            'Sede Principal', 'Av. Primavera 609 - Oficina 507 San Borja, Lima');
			
alter sequence sq_sede_id restart with 2;

INSERT INTO public.persona_usuario(
            id_persona_usuario, apellido_paterno, apellido_materno, nombre, 
            documento_identidad, id_perfil, usuario, clave, estado)
    VALUES (2, 'Renteria', 'Medina', 'Carla', 
            '99999998', 1, 'crenteria', '914b399952b9cff0ba15c8512caa0d42', '1');

INSERT INTO public.clinica(
            id_clinica, ruc, nombre_comercial, nombre_abreviado, direccion_comercial, 
            logo, estado)
    VALUES (2, '42501400474', 'CLINICA LA LUZ S.A.C', 'La Luz', 'Av. Arequipa 1093', 
            '', '1');

INSERT INTO public.administrador(
            id_administrador, estado, id_clinica, id_persona_usuario)
    VALUES (2, '1', 2, 2);

alter sequence sq_persona_usuario_id restart with 3;

-- INSERT INTO public.promocion(
--             id_promocion, costo, fecha_inicio, fecha_fin, estado, id_sede, id_especialidad, 
--             orden, imagen)
--     VALUES (1, 20, '2017-08-04 13:00:00+00', '2017-12-10 13:00:00+00', '1', 1, 1, 
--             1, 'promocion-1.jpg');
-- INSERT INTO public.promocion(
--             id_promocion, costo, fecha_inicio, fecha_fin, estado, id_sede, id_especialidad, 
--             orden, imagen)
--     VALUES (2, 80, '2017-08-04 13:00:00+00', '2017-12-10 13:00:00+00', '1', 1, 1, 
--             2, 'promocion-1.jpg');

