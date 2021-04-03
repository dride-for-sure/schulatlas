import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../../../common/slug';
import Slug from '../../../assemblies/Slug';
import BrandButton from '../../../buttons/BrandButton';
import GridEdit from '../../../grid/cms/GridEdit';
import Headline from '../../../headlines/Headline';
import Loading from '../../../loading/Loading';
import Assembly from './Assembly';

export default function EditPage({
  page,
  pages,
  onChange,
  onUpdateSlug,
  onDeletePage,
  onUploadFile,
  onDeleteFile }) {
  if (!page) {
    return <Loading />;
  }

  return (
    <GridEdit>
      <Headline size="l">{prettifySlug(page.slug)}</Headline>
      <Slug
        slug={page.slug}
        onChange={onUpdateSlug} />
      {page.assemblies.map((assembly) => (
        <Assembly
          key={assembly.id}
          assembly={assembly}
          onChange={onChange}
          onFileUpload={onUploadFile}
          onFileDelete={onDeleteFile}
          pages={pages} />
      ))}
      <BrandButton variant="monochrome" onClick={onDeletePage}>Delete Page</BrandButton>
    </GridEdit>
  );
}

EditPage.propTypes = {
  page: object.isRequired,
  onChange: func.isRequired,
  pages: array.isRequired,
  onUpdateSlug: func.isRequired,
  onDeletePage: func.isRequired,
  onUploadFile: func.isRequired,
  onDeleteFile: func.isRequired,
};
