-- -----------------------------------------------------
-- Table public.ubigeo
-- -----------------------------------------------------
CREATE  TABLE public.ubigeo (
  co_departamento integer ,
  no_departamento character varying(50),
  co_provincia integer ,
  no_provincia character varying(50),
  co_distrito integer ,
  no_distrito character varying(50) ,
  estado character varying(1)  NULL ,
  CONSTRAINT id_ubigeo PRIMARY KEY (co_departamento,co_provincia,co_distrito) )
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.perfil
-- -----------------------------------------------------
CREATE  TABLE public.perfil (
  id_perfil integer NOT NULL ,
  no_perfil character varying(50),
  estado character varying(1)  NULL ,
  CONSTRAINT id_perfil PRIMARY KEY (id_perfil) )
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.persona_usuario
-- -----------------------------------------------------
CREATE  TABLE public.persona_usuario (
  id_persona_usuario integer NOT NULL ,
  apellido_paterno character varying(50),
  apellido_materno character varying(50),
  nombre character varying(50),
  documento_identidad character varying(50),
  tipo_documento character varying(50),
  fecha_nacimiento timestamp with time zone ,
  sexo integer ,
  telefono character varying(50),
  correo character varying(50),
  usuario character varying(50),
  clave character varying(50),
  id_perfil integer ,
  estado character varying(1),
  fe_creacion timestamp with time zone ,
  CONSTRAINT id_persona_usuario PRIMARY KEY (id_persona_usuario) ,
  CONSTRAINT fk_persona_usuario_Perfil1
    FOREIGN KEY (id_perfil )
    REFERENCES public.perfil (id_perfil )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.especialidad
-- -----------------------------------------------------
CREATE  TABLE public.especialidad (
  id_especialidad integer NOT NULL ,
  codigo character varying(50),
  nombre character varying(50),
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  CONSTRAINT id_especialidad PRIMARY KEY (id_especialidad) )
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.clinica
-- -----------------------------------------------------
CREATE  TABLE public.clinica (
  id_clinica integer NOT NULL ,
  ruc character varying(11),
  nombre_comercial character varying(50),
  nombre_abreviado character varying(50),
  direccion_comercial character varying(50),
  logo character varying(50),
  tiempo_cita integer,
  orden integer,
  pagina_web character varying(50),
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  CONSTRAINT id_clinica PRIMARY KEY (id_clinica) )
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.profesional
-- -----------------------------------------------------
CREATE  TABLE public.profesional (
  id_profesional integer NOT NULL ,
  cop character varying(50),
  color_disponibilidad character varying(9),
  color_cita character varying(9),
  tiempo_cita integer,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_persona_usuario integer NOT NULL ,
  id_clinica integer NOT NULL ,
  CONSTRAINT id_profesional PRIMARY KEY (id_profesional) ,
  CONSTRAINT fk_profesional_persona_usuario1
    FOREIGN KEY (id_persona_usuario )
    REFERENCES public.persona_usuario (id_persona_usuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_profesional_clinica FOREIGN KEY (id_clinica)
      REFERENCES public.clinica (id_clinica) 
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.sede
-- -----------------------------------------------------
CREATE  TABLE public.sede (
  id_sede integer NOT NULL ,
  codigo integer,
  nombre character varying(50),
  direccion character varying(50),
  co_departamento integer ,
  co_provincia integer ,
  co_distrito integer ,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_clinica integer NOT NULL ,
  CONSTRAINT id_sede PRIMARY KEY (id_sede) ,
  CONSTRAINT fk_sede_clinica1
    FOREIGN KEY (id_clinica )
    REFERENCES public.clinica (id_clinica )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_sede_ubigeo FOREIGN KEY (co_departamento, co_provincia, co_distrito)
      REFERENCES public.ubigeo (co_departamento, co_provincia, co_distrito) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT u_sede UNIQUE (id_clinica, codigo))
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.sede_especialidad
-- -----------------------------------------------------
CREATE  TABLE public.sede_especialidad (
  id_especialidad integer NOT NULL ,
  id_sede integer NOT NULL ,
  costo_consulta numeric ,
  costo numeric ,
  estado character varying(1),
  CONSTRAINT pk_sede_especialidad PRIMARY KEY (id_especialidad, id_sede) ,
  CONSTRAINT fk_sede_especialidad_especialidad1
    FOREIGN KEY (id_especialidad )
    REFERENCES public.especialidad (id_especialidad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_sede_especialidad_sede1
    FOREIGN KEY (id_sede )
    REFERENCES public.sede (id_sede )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.paciente
-- -----------------------------------------------------
CREATE  TABLE public.paciente (
  id_paciente integer NOT NULL ,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_persona_usuario integer NOT NULL ,
  CONSTRAINT id_paciente PRIMARY KEY (id_paciente) ,
  CONSTRAINT fk_paciente_persona_usuario1
    FOREIGN KEY (id_persona_usuario )
    REFERENCES public.persona_usuario (id_persona_usuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.subespecialidad
-- -----------------------------------------------------
CREATE  TABLE public.subespecialidad (
  id_subespecialidad integer NOT NULL ,
  nombre character varying(50),
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_especialidad integer NOT NULL ,
  CONSTRAINT id_subespecialidad PRIMARY KEY (id_subespecialidad) ,
  CONSTRAINT fk_subespecialidad_especialidad1
    FOREIGN KEY (id_especialidad )
    REFERENCES public.especialidad (id_especialidad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.sede_esp_subesp
-- -----------------------------------------------------
CREATE  TABLE public.sede_esp_subesp (
  id_sede integer NOT NULL ,
  id_especialidad integer NOT NULL ,
  id_subespecialidad integer NOT NULL ,
  costo numeric ,
  CONSTRAINT pk_sede_esp_subesp PRIMARY KEY (id_sede, id_especialidad, id_subespecialidad) ,
  CONSTRAINT fk_sede_esp_subesp_sede_especialidad1
    FOREIGN KEY (id_sede, id_especialidad)
    REFERENCES public.sede_especialidad (id_sede, id_especialidad)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_sede_esp_subesp_subespecialidad1
    FOREIGN KEY (id_subespecialidad )
    REFERENCES public.subespecialidad (id_subespecialidad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.promocion
-- -----------------------------------------------------
CREATE  TABLE public.promocion (
  id_promocion integer NOT NULL ,
  costo numeric ,
  fecha_inicio timestamp with time zone ,
  fecha_fin timestamp with time zone ,
  orden integer,
  imagen character varying(50),
  descripcion character varying(200),
  estado character varying(1)  NULL ,
  us_creacion character varying(50),
  fe_creacion timestamp with time zone ,
  us_modificacion character varying(50),
  fe_modificacion timestamp with time zone ,
  id_sede integer NOT NULL,
  id_especialidad integer NOT NULL,
  CONSTRAINT id_promocion PRIMARY KEY (id_promocion) ,
  CONSTRAINT fk_promocion_sede_especialidad1 FOREIGN KEY (id_sede, id_especialidad)
      REFERENCES public.sede_especialidad (id_sede, id_especialidad) MATCH SIMPLE
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.administrador
-- -----------------------------------------------------
CREATE  TABLE public.administrador (
  id_administrador integer NOT NULL ,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_clinica integer NOT NULL ,
  id_persona_usuario integer NOT NULL ,
  CONSTRAINT id_administrador PRIMARY KEY (id_administrador) ,
  CONSTRAINT fk_administrador_clinica1
    FOREIGN KEY (id_clinica )
    REFERENCES public.clinica (id_clinica )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_administrador_persona_usuario1
    FOREIGN KEY (id_persona_usuario )
    REFERENCES public.persona_usuario (id_persona_usuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.asistente
-- -----------------------------------------------------
CREATE  TABLE public.asistente (
  id_asistente integer NOT NULL ,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_clinica integer NOT NULL ,
  id_persona_usuario integer NOT NULL ,
  CONSTRAINT id_asistente PRIMARY KEY (id_asistente) ,
  CONSTRAINT fk_asistente_clinica1
    FOREIGN KEY (id_clinica )
    REFERENCES public.clinica (id_clinica )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_asistente_persona_usuario1
    FOREIGN KEY (id_persona_usuario )
    REFERENCES public.persona_usuario (id_persona_usuario )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.sede_esp_prof
-- -----------------------------------------------------
CREATE  TABLE public.sede_esp_prof (
  id_profesional integer NOT NULL ,
  id_sede integer NOT NULL ,
  id_especialidad integer NOT NULL,
  estado character varying(1),
  CONSTRAINT pk_sede_esp_prof PRIMARY KEY (id_sede, id_especialidad, id_profesional),
  CONSTRAINT fk_sede_esp_prof_2 FOREIGN KEY (id_sede, id_especialidad)
      REFERENCES public.sede_especialidad (id_sede, id_especialidad) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sede_esp_prof_profesional1
    FOREIGN KEY (id_profesional )
    REFERENCES public.profesional (id_profesional )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.disponibilidad
-- -----------------------------------------------------
CREATE  TABLE public.disponibilidad (
  id_disponibilidad integer NOT NULL ,
  fecha_inicio timestamp with time zone ,
  fecha_fin timestamp with time zone ,
  repeticion_tipo integer ,
  repeticion_cada integer ,
  repeticion_dias character varying(50),
  repeticion_inicio timestamp with time zone ,
  repeticion_fin timestamp with time zone ,
  repeticion_veces integer ,
  id_profesional integer NOT NULL ,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_sede integer NOT NULL ,
  id_especialidad integer NOT NULL ,
  CONSTRAINT id_disponibilidad PRIMARY KEY (id_disponibilidad) ,
  CONSTRAINT fk_disponibilidad_sede_esp_prof FOREIGN KEY (id_sede, id_especialidad, id_profesional)
      REFERENCES public.sede_esp_prof (id_sede, id_especialidad, id_profesional) 
      ON UPDATE NO ACTION ON DELETE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.disponibilidad_evento
-- -----------------------------------------------------
CREATE  TABLE public.disponibilidad_evento (
  id_disponibilidad_evento integer NOT NULL ,
  fecha_inicio timestamp with time zone ,
  fecha_fin timestamp with time zone ,
  id_disponibilidad integer NOT NULL ,
  estado character varying(1)  NULL ,
  CONSTRAINT pk_disponibilidad_evento PRIMARY KEY (id_disponibilidad_evento) ,
  CONSTRAINT fk_disponibilidad_evento_disponibilidad
    FOREIGN KEY (id_disponibilidad )
    REFERENCES public.disponibilidad (id_disponibilidad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.cita
-- -----------------------------------------------------
CREATE  TABLE public.cita (
  id_cita integer NOT NULL ,
  codigo character varying(50) NOT NULL,
  fecha_atencion timestamp with time zone ,
  estado_cita character varying(50),
  comentario character varying(100) NULL ,
  tiempo_cita integer,
  estado character varying(1)  NULL ,
  us_creacion integer ,
  fe_creacion timestamp with time zone ,
  us_modificacion integer ,
  fe_modificacion timestamp with time zone ,
  id_profesional integer NOT NULL ,
  id_sede integer NOT NULL ,
  id_especialidad integer NOT NULL ,
  id_paciente integer NOT NULL ,
  id_subespecialidad integer ,
  id_promocion integer ,
  CONSTRAINT id_cita PRIMARY KEY (id_cita) ,
  CONSTRAINT fk_cita_sede_esp_prof FOREIGN KEY (id_sede, id_especialidad, id_profesional)
      REFERENCES public.sede_esp_prof (id_sede, id_especialidad, id_profesional) 
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_citas_paciente1
    FOREIGN KEY (id_paciente )
    REFERENCES public.paciente (id_paciente )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_cita_sede_esp_subesp1
    FOREIGN KEY (id_sede, id_especialidad, id_subespecialidad)
    REFERENCES public.sede_esp_subesp (id_sede, id_especialidad, id_subespecialidad)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_cita_promocion1
    FOREIGN KEY (id_promocion )
    REFERENCES public.promocion (id_promocion )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT u_codigo UNIQUE (codigo))
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.opcion
-- -----------------------------------------------------
CREATE  TABLE public.opcion (
  id_opcion integer NOT NULL ,
  no_opcion character varying(50),
  id_padre integer,
  de_url character varying(50),
  estado character varying(1)  NULL ,
  CONSTRAINT id_opcion PRIMARY KEY (id_opcion) )
WITH (  OIDS=FALSE);


-- -----------------------------------------------------
-- Table public.perfil_opcion
-- -----------------------------------------------------
CREATE  TABLE public.perfil_opcion (
  id_perfil integer NOT NULL ,
  id_opcion integer NOT NULL ,
  CONSTRAINT pk_perfil_opcion PRIMARY KEY (id_perfil, id_opcion) ,
  CONSTRAINT fk_perfil_opcion_perfil1
    FOREIGN KEY (id_perfil )
    REFERENCES public.perfil (id_perfil )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_perfil_opcion_opcion1
    FOREIGN KEY (id_opcion )
    REFERENCES public.opcion (id_opcion )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
WITH (  OIDS=FALSE);