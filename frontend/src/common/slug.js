export const prettifySlug = (slug) => slug.replaceAll('-', ' ');

export const escapeSlug = (slug) => slug.trim().replace(/[^a-zA-Z0-9-_]/g, '');

/**
 * TODO:
 * On Enter -> fulltext
 * Tests schreiben
 */
