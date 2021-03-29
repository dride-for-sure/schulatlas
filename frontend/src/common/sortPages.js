const sortPages = (pages) =>
  pages.sort((a, b) => {
    if (a.slug.toLowerCase() < b.slug.toLowerCase()) {
      return -1;
    }
    if (a.slug.toLowerCase() > b.slug.toLowerCase()) {
      return 1;
    }
    return 0;
  });

export default sortPages;
