import { v4 as uuid } from 'uuid';

export const getAssemblyVariants = (type) => {
  switch (type) {
    case 'hero':
      return ['primary', 'secondary'];
    case 'feature-card':
      return ['image-left', 'image-right'];
    default:
      return '';
  }
};

export const getSchoolTemplate = () => ({
  number: `new-school-${uuid()}`,
  newSchool: true,
  name: 'New School',
  address: {
    street: '',
    number: '',
    postcode: '',
    city: '',
  },
  contact: {
    phone: '',
    email: '',
    url: '',
  },
  image: '',
  type: '',
  updated: Date.now(),
  userId: '',
  properties: [],
});

export const getPageTemplate = (name) => {
  switch (name) {
    case 'highlights':
      return {
        slug: 'new-highlights-page',
        newPage: true,
        landingPage: false,
        updated: Date.now(),
        assemblies: [
          {
            type: 'seo',
            components: [
              { type: 'title' },
              { type: 'description' },
            ],
          },
          {
            type: 'hero',
            variant: 'primary',
            components: [
              { type: 'title' },
              { type: 'subtitle' },
              { type: 'button' },
              { type: 'button' },
              { type: 'image' },
            ],
          },
          {
            type: 'sponsors',
            components: [
              { type: 'title' },
              { type: 'image' },
              { type: 'image' },
              { type: 'image' },
            ],
          },
          {
            type: 'feature-card',
            variant: 'image-left',
            components: [
              { type: 'title' },
              { type: 'subtitle' },
              { type: 'paragraph' },
              { type: 'image' },
            ],
          },
          {
            type: 'feature-card',
            variant: 'image-right',
            components: [
              { type: 'title' },
              { type: 'subtitle' },
              { type: 'paragraph' },
              { type: 'image' },
            ],
          },
          {
            type: 'cards',
            components: [
              { type: 'card' },
              { type: 'card' },
              { type: 'card' },
            ],
          },
          {
            type: 'cta',
            components: [
              { type: 'title' },
              { type: 'paragraph' },
              { type: 'button' },
              { type: 'button' },
            ],
          },
        ],
      };
    case 'contentfull':
      return {
        slug: 'new-contentfull-page',
        landingPage: false,
        newPage: true,
        updated: Date.now(),
        assemblies: [
          {
            type: 'seo',
            components: [
              { type: 'title' },
              { type: 'description' },
            ],
          },
          {
            type: 'hero',
            variant: 'secondary',
            components: [
              { type: 'title row 1' },
              { type: 'title row 2' },
              { type: 'subtitle' },
              { type: 'image' },
            ],
          },
          {
            type: 'textblock',
            components: [
              { type: 'title' },
              { type: 'subtitle' },
              { type: 'paragraph' },
              { type: 'image' },
              { type: 'paragraph' },
            ],
          },
          {
            type: 'cards',
            components: [
              { type: 'card' },
              { type: 'card' },
              { type: 'card' },
            ],
          },
          {
            type: 'cta',
            components: [
              { type: 'title' },
              { type: 'paragraph' },
              { type: 'button' },
              { type: 'button' },
            ],
          },
        ],
      };
    default:
      return '';
  }
};
