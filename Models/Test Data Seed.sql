-- Locations
-- Counties
INSERT INTO country (`name`) VALUES ('Colombia');
-- States / Provinces
INSERT INTO state_province (`name`, `country`) VALUES
('Tolima', 1), ('Antioquia', 1);
-- Cities
INSERT INTO city (`name`, `state_province`) VALUES
('Ibague', 1), ('Medellin', 2);

-- People
INSERT INTO person (`id_person`, `first_name`, `last_name`, `birth_date`, `id_expedition_city`, `identification_type`, `gender`)  VALUES
('1110567597', 'Armando', 'Castillo', '1996-02-04', 1, 'C', 'M'), ('1110562084', 'Julissa', 'Gonzalez', '1995-08-21', 1, 'C', 'F');

-- Clients
INSERT INTO client (`id_client`, `city`, `academic_level`, `social_level`, `civil_status`, `address`, `phone`) VALUES ('1110567597', 1, 'P', 3, 'S', 'Calle 39 #4-89', '3106492237');

-- Employees
INSERT INTO employee (`id_employee`, `role`, `password_salt`, `password_hash`) VALUES ('1110562084', 1, 'abcd1234', 'ABC123');

-- Companies
INSERT INTO company (`id_company`, `name`) VALUES ('900934988', 'SunDevs');

-- Company Contact Information
INSERT INTO contact_information (`type`, `name`, `contact`, `company`) VALUES ('MOBILE PHONE', 'SunDevs Mobile', '3127978329', '900934988');

-- Occupational Records
INSERT INTO occupational_record (`client`, `company`, `start_date`, `end_date`, `role`, `registered_on`) VALUES ('1110567597', '900934988', '2017-02-20', NULL, 'Software Developer', '2019-05-23');

-- Habits
INSERT INTO habit (`description`) VALUES ('Smoking');

-- Client Habits
INSERT INTO client_habit (`client`, `habit`, `weekly_hours_intensity`, `registered_on`) VALUES ('1110567597', 1, 4, '2019-05-23');

-- Trauma History
INSERT INTO trauma_history (`client`, `trauma_nature`, `sequels`, `occurrence_date`, `registered_on`) VALUES ('1110567597', 'Concussion', 'Dizziness and head pain', '2011-09-15', '2019-05-23');

-- Medical Case Types
INSERT INTO medical_case_type (`name`) VALUES
('Night Terrors'), ('Parkinson');

-- Client Case History
INSERT INTO case_history (`case_type`, `client`, `background_type`, `registered_on`) VALUES
(1, '1110567597', 'P', '2019-05-23'), (2, '1110567597', 'F', '2019-05-23');

-- Informed Consents
INSERT INTO informed_consent (`client`, `employee`, `contracting_company`, `check_type`, `work_in_heights`, `date`) VALUES ('1110567597', '1110562084', '900934988', 1, 0, '2019-05-23');

-- Professional Risks
INSERT INTO professional_risk (`name`, `type`) VALUES ('Carpal tunnel syndrome', 'Ergonomic');

-- Informed Consent Professional Risks
INSERT INTO informed_consent_professional_risk (`informed_consent`, `professional_risk`) VALUES (1, 1);

-- Medical Anomalies
INSERT INTO medical_anomaly (`name`, `type`) VALUES ('Spine deviation', 'osseous');

-- Post Exam Actions
INSERT INTO post_exam_action (`name`, `type`) VALUES ('Usage of ergonomic aids', 'Occupational');

-- Physical Check
INSERT INTO physical_check (`id_physical_check`, `employee`, `weight_kilograms`, `height_centimeters`, `pulse_beats_per_minute`, `respiratory_frequency_per_minute`, `body_temperature`, `blood_pressure_standing`, `blood_pressure_laying_down`, `handedness`, `diagnostics`, `conclusions`, `recommendations`) VALUES (1, '1110562084', 81.7, 184, 80, 17, 37.2, 100.3, 87.9, 'R', 'Slight spine deviation.', 'Overall healthy man.', 'Mind seating position at work.');

-- Physical Check Medical Anomalies
INSERT INTO physical_check_medical_anomaly (`physical_check`, `medical_anomaly`, `observations`) VALUES (1, 1, 'Vertebrae 5, 6 and 7 display slight deviation');

-- Aptitude Concept
INSERT INTO work_aptitude_concept (`id_work_aptitude_concept`, `employee`, `work_aptitude`, `work_in_heights_aptitude`, `concept`, `concept_observations`, `psychotechnic_test`, `recommendations`) VALUES (1, '1110562084', 'P', NULL, 'P', 'May continue working while adopting better sitting discipline', 'P', 'Improve seating position. Try to acquire an ergonomic chair.');

-- Work Aptitude Concept Post Exam Action
INSERT INTO work_aptitude_concept_post_exam_action (`work_aptitude_concept`, `post_exam_action`, `observations`) VALUES (1, 1, 'Look for padded back support in an ergonomic chair.');

-- Laboratory Exams
INSERT INTO laboratory_exam (`name`, `informed_consent`) VALUES ('Spinal Radiography', 1);

-- Laboratory Exam Attachments
INSERT INTO laboratory_exam_attachment (`name`, `uri`, `laboratory_exam`) VALUES
('Side view', 'https://cdn.sao.com.co/laboratory_exams/1.bmp', 1), ('Front view', 'https://cdn.sao.com.co/laboratory_exams/2.bmp', 1);