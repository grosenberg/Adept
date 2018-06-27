// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/java/parser/Java8Parser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.java.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Java8Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Java8ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Java8Parser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(Java8Parser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(Java8Parser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(Java8Parser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassModifier(Java8Parser.ClassModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(Java8Parser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(Java8Parser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassTypeList(Java8Parser.ClassTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(Java8Parser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMemberDeclaration(Java8Parser.ClassMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#fieldModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldModifier(Java8Parser.FieldModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(Java8Parser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(Java8Parser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(Java8Parser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#numericType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericType(Java8Parser.NumericTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(Java8Parser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(Java8Parser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(Java8Parser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#dims}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDims(Java8Parser.DimsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(Java8Parser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(Java8Parser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#additionalBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionalBound(Java8Parser.AdditionalBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(Java8Parser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentList(Java8Parser.TypeArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(Java8Parser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#wildcard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcard(Java8Parser.WildcardContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodModifier(Java8Parser.MethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodHeader(Java8Parser.MethodHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult(Java8Parser.ResultContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(Java8Parser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(Java8Parser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(Java8Parser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#receiverParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReceiverParameter(Java8Parser.ReceiverParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#throws_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrows_(Java8Parser.Throws_Context ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#exceptionTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#exceptionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionType(Java8Parser.ExceptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(Java8Parser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constructorModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorModifier(Java8Parser.ConstructorModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constructorDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constructorBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBody(Java8Parser.ConstructorBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(Java8Parser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#enumConstantList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantList(Java8Parser.EnumConstantListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(Java8Parser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(Java8Parser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMemberDeclaration(Java8Parser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constantDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDeclaration(Java8Parser.ConstantDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#constantModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantModifier(Java8Parser.ConstantModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodModifier(Java8Parser.InterfaceMethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#annotationTypeMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeMemberDeclaration(Java8Parser.AnnotationTypeMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(Java8Parser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(Java8Parser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#elementValuePairList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairList(Java8Parser.ElementValuePairListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(Java8Parser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(Java8Parser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#elementValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueList(Java8Parser.ElementValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(Java8Parser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#variableInitializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializerList(Java8Parser.VariableInitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(Java8Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(Java8Parser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Java8Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#statementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpression(Java8Parser.StatementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#switchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlock(Java8Parser.SwitchBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(Java8Parser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(Java8Parser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(Java8Parser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(Java8Parser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#statementExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpressionList(Java8Parser.StatementExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#catches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatches(Java8Parser.CatchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#catchType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchType(Java8Parser.CatchTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#resourceList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceList(Java8Parser.ResourceListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#resource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResource(Java8Parser.ResourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(Java8Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray(Java8Parser.PrimaryNoNewArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray_lfno_arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_arrayAccess(Java8Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(Java8Parser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess_lf_primary(Java8Parser.ArrayAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess_lfno_primary(Java8Parser.ArrayAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#arrayAccessExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpression(Java8Parser.ArrayAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classInstanceCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classInstanceCreationExpression_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression_lf_primary(Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#classInstanceCreationExpression_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression_lfno_primary(Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#instanceCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCreationExpression(Java8Parser.InstanceCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentsOrDiamond(Java8Parser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#fieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess(Java8Parser.FieldAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#fieldAccess_lf_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess_lf_primary(Java8Parser.FieldAccess_lf_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#fieldAccess_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess_lfno_primary(Java8Parser.FieldAccess_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation(Java8Parser.MethodInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodInvocation_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(Java8Parser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference(Java8Parser.MethodReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#methodReference_lfno_primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference_lfno_primary(Java8Parser.MethodReference_lfno_primaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#dimExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimExpr(Java8Parser.DimExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Java8Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(Java8Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(Java8Parser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(Java8Parser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(Java8Parser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(Java8Parser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java8Parser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(Java8Parser.PostfixExpressionContext ctx);
}