import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../../../common/slug';
import convertTimeStampToDate from '../../../../common/timeStamp';
import Slug from '../../../assemblies/cms/Slug';
import BrandButton from '../../../buttons/BrandButton';
import HeadlineWithSubtitle from '../../../headlines/HeadlineWithSubtitle';
import Loading from '../../../loading/Loading';
import GridEdit from '../../../structures/GridEdit';
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
      <HeadlineWithSubtitle
        size="l"
        title={prettifySlug(page.slug)}
        subtitle={`Updated on ${convertTimeStampToDate(page.updated)} ${page.userId ? `by ${page.userId}` : ''}`} />
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
