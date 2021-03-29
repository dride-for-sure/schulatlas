const getSeoDescription = (pages, slug) => {
  const wantedPage = pages.find((page) => page.slug === slug);
  if (wantedPage) {
    const hero = wantedPage.assemblies.find((assembly) => assembly.type === 'hero');
    if (hero) {
      const titleComp = hero.components.find((component) => component.type === 'title');
      const subtitleComp = hero.components.find((component) => component.type === 'subtitle');
      if (titleComp && subtitleComp) {
        return `${titleComp.content} - ${subtitleComp.content}`;
      }
    }
  }
  return '';
};

export default getSeoDescription;
