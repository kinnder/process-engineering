package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;

import planning.method.SystemTransformations;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	@Override
	public OntModel combine(SystemTransformations object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_systemTransformations = m.createClass(NS + "System Transformations");
		ontClass_systemTransformations.addLabel("System Transformations", "en");
		ontClass_systemTransformations.addLabel("Трансформации системы", "ru");

		OntClass ontClass_systemTransformation = m.createClass(NS + "System Transformation");
		ontClass_systemTransformation.addLabel("System Transformation", "en");
		ontClass_systemTransformation.addLabel("Трансформация системы", "ru");

		OntClass ontClass_systemTemplate = m.createClass(NS + "System Template");
		ontClass_systemTemplate.addLabel("System Template", "en");
		ontClass_systemTemplate.addLabel("Шаблон системы", "ru");

		OntClass ontClass_transformations = m.createClass(NS + "Transformations");
		ontClass_transformations.addLabel("Transformations", "en");
		ontClass_transformations.addLabel("Трансформации", "ru");

		OntClass ontClass_action = m.createClass(NS + "Action");
		ontClass_action.addLabel("Action", "en");
		ontClass_action.addLabel("Действие", "ru");

		OntClass ontClass_objectTemplate = m.createClass(NS + "Object Template");
		ontClass_objectTemplate.addLabel("Object Template", "en");
		ontClass_objectTemplate.addLabel("Шаблон объекта", "ru");

		OntClass ontClass_linkTemplate = m.createClass(NS + "Link Template");
		ontClass_linkTemplate.addLabel("Link Template", "en");
		ontClass_linkTemplate.addLabel("Шаблон связи", "ru");

		OntClass ontClass_attributeTemplate = m.createClass(NS + "Attribute Template");
		ontClass_attributeTemplate.addLabel("Attribute Template", "en");
		ontClass_attributeTemplate.addLabel("Шаблон атрибута", "ru");

		ObjectProperty ontObjectProperty_hasSystemTransformation = m
				.createObjectProperty(NS + "hasSystemTransformation");
		ontObjectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		ontObjectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		ontObjectProperty_hasSystemTransformation.addDomain(ontClass_systemTransformations);
		ontObjectProperty_hasSystemTransformation.addRange(ontClass_systemTransformation);

		ObjectProperty ontObjectProperty_isSystemTransformationOf = m
				.createObjectProperty(NS + "isSystemTransformationOf");
		ontObjectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		ontObjectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		ontObjectProperty_isSystemTransformationOf.addDomain(ontClass_systemTransformation);
		ontObjectProperty_isSystemTransformationOf.addRange(ontClass_systemTransformations);

		ontObjectProperty_hasSystemTransformation.addInverseOf(ontObjectProperty_isSystemTransformationOf);
		ontObjectProperty_isSystemTransformationOf.addInverseOf(ontObjectProperty_hasSystemTransformation);

		ObjectProperty ontObjectProperty_hasSystemTemplate = m.createObjectProperty(NS + "hasSystemTemplate");
		ontObjectProperty_hasSystemTemplate.addLabel("has system template", "en");
		ontObjectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		ontObjectProperty_hasSystemTemplate.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasSystemTemplate.addRange(ontClass_systemTemplate);

		ObjectProperty ontObjectProperty_isSystemTemplateOf = m.createObjectProperty(NS + "isSystemTemplateOf");
		ontObjectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		ontObjectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		ontObjectProperty_isSystemTemplateOf.addDomain(ontClass_systemTemplate);
		ontObjectProperty_isSystemTemplateOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasSystemTemplate.addInverseOf(ontObjectProperty_isSystemTemplateOf);
		ontObjectProperty_isSystemTemplateOf.addInverseOf(ontObjectProperty_hasSystemTemplate);

		ObjectProperty ontObjectProperty_hasTransformations = m.createObjectProperty(NS + "hasTransformations");
		ontObjectProperty_hasTransformations.addLabel("has transformations", "en");
		ontObjectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		ontObjectProperty_hasTransformations.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasTransformations.addRange(ontClass_transformations);

		ObjectProperty ontObjectProperty_areTransformationsOf = m.createObjectProperty(NS + "areTransformationsOf");
		ontObjectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		ontObjectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		ontObjectProperty_areTransformationsOf.addDomain(ontClass_transformations);
		ontObjectProperty_areTransformationsOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasTransformations.addInverseOf(ontObjectProperty_areTransformationsOf);
		ontObjectProperty_areTransformationsOf.addInverseOf(ontObjectProperty_hasTransformations);

		ObjectProperty ontObjectProperty_hasAction = m.createObjectProperty(NS + "hasAction");
		ontObjectProperty_hasAction.addLabel("has action", "en");
		ontObjectProperty_hasAction.addLabel("имеет действие", "ru");
		ontObjectProperty_hasAction.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasAction.addRange(ontClass_action);

		ObjectProperty ontObjectProperty_isActionOf = m.createObjectProperty(NS + "isActionOf");
		ontObjectProperty_isActionOf.addLabel("is action of", "en");
		ontObjectProperty_isActionOf.addLabel("является действием для", "ru");
		ontObjectProperty_isActionOf.addDomain(ontClass_action);
		ontObjectProperty_isActionOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasAction.addInverseOf(ontObjectProperty_isActionOf);
		ontObjectProperty_isActionOf.addInverseOf(ontObjectProperty_hasAction);

		ObjectProperty ontObjectProperty_hasObjectTemplate = m.createObjectProperty(NS + "hasObjectTemplate");
		ontObjectProperty_hasObjectTemplate.addLabel("has objectTemplate", "en");
		ontObjectProperty_hasObjectTemplate.addLabel("имеет шаблон объекта", "ru");
		ontObjectProperty_hasObjectTemplate.addDomain(ontClass_systemTemplate);
		ontObjectProperty_hasObjectTemplate.addRange(ontClass_objectTemplate);

		ObjectProperty ontObjectProperty_isObjectTemplateOf = m.createObjectProperty(NS + "isObjectTemplateOf");
		ontObjectProperty_isObjectTemplateOf.addLabel("is object template of", "en");
		ontObjectProperty_isObjectTemplateOf.addLabel("является шаблоном объекта для", "ru");
		ontObjectProperty_isObjectTemplateOf.addDomain(ontClass_objectTemplate);
		ontObjectProperty_isObjectTemplateOf.addRange(ontClass_systemTemplate);

		ontObjectProperty_hasObjectTemplate.addInverseOf(ontObjectProperty_isObjectTemplateOf);
		ontObjectProperty_isObjectTemplateOf.addInverseOf(ontObjectProperty_hasObjectTemplate);

		ObjectProperty ontObjectProperty_hasLinkTemplate = m.createObjectProperty(NS + "hasLinkTemplate");
		ontObjectProperty_hasLinkTemplate.addLabel("has link template", "en");
		ontObjectProperty_hasLinkTemplate.addLabel("имеет шаблон связи", "ru");
		ontObjectProperty_hasLinkTemplate.addDomain(ontClass_systemTemplate);
		ontObjectProperty_hasLinkTemplate.addRange(ontClass_linkTemplate);

		ObjectProperty ontObjectProperty_isLinkTemplateOf = m.createObjectProperty(NS + "isLinkTemplateOf");
		ontObjectProperty_isLinkTemplateOf.addLabel("is link template of", "en");
		ontObjectProperty_isLinkTemplateOf.addLabel("является шаблоном связи для", "ru");
		ontObjectProperty_isLinkTemplateOf.addDomain(ontClass_linkTemplate);
		ontObjectProperty_isLinkTemplateOf.addRange(ontClass_systemTemplate);

		ontObjectProperty_hasLinkTemplate.addInverseOf(ontObjectProperty_isLinkTemplateOf);
		ontObjectProperty_isLinkTemplateOf.addInverseOf(ontObjectProperty_hasLinkTemplate);

		ObjectProperty ontObjectProperty_hasAttributeTemplate = m.createObjectProperty(NS + "hasAttributeTemplate");
		ontObjectProperty_hasAttributeTemplate.addLabel("has attribute template", "en");
		ontObjectProperty_hasAttributeTemplate.addLabel("имеет шаблон атрибута", "ru");
		ontObjectProperty_hasAttributeTemplate.addDomain(ontClass_objectTemplate);
		ontObjectProperty_hasAttributeTemplate.addRange(ontClass_attributeTemplate);

		ObjectProperty ontObjectProperty_isAttributeTemplateOf = m.createObjectProperty(NS + "isAttributeTemplateOf");
		ontObjectProperty_isAttributeTemplateOf.addLabel("is attribute template of", "en");
		ontObjectProperty_isAttributeTemplateOf.addLabel("является шаблоном атрибута для", "ru");
		ontObjectProperty_isAttributeTemplateOf.addDomain(ontClass_attributeTemplate);
		ontObjectProperty_isAttributeTemplateOf.addRange(ontClass_objectTemplate);

		ontObjectProperty_hasAttributeTemplate.addInverseOf(ontObjectProperty_isAttributeTemplateOf);
		ontObjectProperty_isAttributeTemplateOf.addInverseOf(ontObjectProperty_hasAttributeTemplate);

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addDomain(ontClass_systemTransformation);
		ontDatatypeProperty_name.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId = m.createDatatypeProperty(NS + "objectId");
		ontDatatypeProperty_objectId.addLabel("objectId", "en");
		ontDatatypeProperty_objectId.addLabel("идентификатор объекта", "ru");
		ontDatatypeProperty_objectId.addDomain(ontClass_objectTemplate);
		ontDatatypeProperty_objectId.addRange(XSD.xstring);

		ontDatatypeProperty_name.addDomain(ontClass_attributeTemplate);

		DatatypeProperty ontDatatypeProperty_value = m.createDatatypeProperty(NS + "value");
		ontDatatypeProperty_value.addLabel("value", "en");
		ontDatatypeProperty_value.addLabel("значение", "ru");
		ontDatatypeProperty_value.addDomain(ontClass_attributeTemplate);
		ontDatatypeProperty_value.addRange(XSD.xstring);
		ontDatatypeProperty_value.addRange(XSD.xboolean);
		ontDatatypeProperty_value.addRange(XSD.xint);

		ontDatatypeProperty_name.addDomain(ontClass_linkTemplate);

		DatatypeProperty ontDatatypeProperty_objectId1 = m.createDatatypeProperty(NS + "objectId1");
		ontDatatypeProperty_objectId1.addLabel("objectId1", "en");
		ontDatatypeProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		ontDatatypeProperty_objectId1.addDomain(ontClass_linkTemplate);
		ontDatatypeProperty_objectId1.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId2 = m.createDatatypeProperty(NS + "objectId2");
		ontDatatypeProperty_objectId2.addLabel("objectId2", "en");
		ontDatatypeProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		ontDatatypeProperty_objectId2.addDomain(ontClass_linkTemplate);
		ontDatatypeProperty_objectId2.addRange(XSD.xstring);

		// transformations

		// Individuals

		return m;
	}

	@Override
	public SystemTransformations parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}

}
