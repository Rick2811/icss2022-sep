package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.AST;

public class Generator {

	public String generate(AST ast) {
        return generateStylesheet(ast.root);


private string generateStylesheet(nl.han.ica.icss.ast.Stylesheet stylesheet) {
	return generatestylerule ((stylerule)node.getchilderen().get(0));
	;als je meer stylerules hebt moet je die ook loopen dus niet hoe het hierboven gaat maar met meer in de code
	}
private string generatestylerule(nl.han.ica.icss.ast.Stylerule stylerule){
	node.selectors.get(0).toString()
			+ "{\n"
			+ generateDeclarations((declaration)node.body.get(0)) +
			+ "}";
	
}

private string generateDeclarations(nl.han.ica.icss.ast.Declaration declaration){
	return "    " + declaration.property.name + ": "
			+ generateExpression((expression)declaration.getChildren().get(0)) + ";\n";
}
