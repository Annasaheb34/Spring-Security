"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.analyzeEmbedded = void 0;
const eslint_1 = require("linting/eslint");
const embedded_1 = require("parsing/embedded");
const helpers_1 = require("helpers");
/**
 * Analyzes a file containing JS snippets
 *
 * Analyzing embedded JS is part of analyzing inline JavaScript code
 * within various file formats: YAML, HTML, etc. The function first starts by parsing
 * the whole file to validate its syntax and to get in return an abstract syntax
 * tree. This abstract syntax tree is then used to extract embedded JavaScript
 * code. As files might embed several JavaScript snippets, the function
 * builds an ESLint SourceCode instance for each snippet using the same utility
 * as for building source code for regular JavaScript analysis inputs. However,
 * since a file can potentially produce multiple ESLint SourceCode instances,
 * the function stops to the first JavaScript parsing error and returns it without
 * considering any other. If all abstract syntax trees are valid, the function
 * then proceeds with linting each of them, aggregates, and returns the results.
 *
 * The analysis requires that global linter wrapper is initialized.
 *
 * @param input the analysis input
 * @param language the language of the file containing the JS code
 * @returns the analysis output
 */
function analyzeEmbedded(input, language) {
    (0, helpers_1.debug)(`Analyzing file "${input.filePath}" with linterId "${input.linterId}"`);
    const linter = (0, eslint_1.getLinter)(input.linterId);
    const extendedSourceCodes = (0, embedded_1.buildSourceCodes)(input, language);
    const aggregatedIssues = [];
    const aggregatedUcfgPaths = [];
    for (const extendedSourceCode of extendedSourceCodes) {
        const { issues, ucfgPaths } = linter.lint(extendedSourceCode, extendedSourceCode.syntheticFilePath, 'MAIN');
        const filteredIssues = removeNonJsIssues(extendedSourceCode, issues);
        aggregatedIssues.push(...filteredIssues);
        aggregatedUcfgPaths.push(...ucfgPaths);
    }
    return { issues: aggregatedIssues, ucfgPaths: aggregatedUcfgPaths };
    /**
     * Filters out issues outside of JS code.
     *
     * This is necessary because we patch the SourceCode object
     * to include the whole file in its properties outside its AST.
     * So rules that operate on SourceCode.text get flagged.
     */
    function removeNonJsIssues(sourceCode, issues) {
        const [jsStart, jsEnd] = sourceCode.ast.range.map(offset => sourceCode.getLocFromIndex(offset));
        return issues.filter(issue => {
            const issueStart = { line: issue.line, column: issue.column };
            return isBeforeOrEqual(jsStart, issueStart) && isBeforeOrEqual(issueStart, jsEnd);
        });
        function isBeforeOrEqual(a, b) {
            if (a.line < b.line) {
                return true;
            }
            else if (a.line > b.line) {
                return false;
            }
            else {
                return a.column <= b.column;
            }
        }
    }
}
exports.analyzeEmbedded = analyzeEmbedded;
//# sourceMappingURL=analyzer.js.map