export const prettifySlug = (slug) => slug.replaceAll('-', ' ');

export const escapeSlug = (slug) => slug.trim().replaceAll(' ', '-');
